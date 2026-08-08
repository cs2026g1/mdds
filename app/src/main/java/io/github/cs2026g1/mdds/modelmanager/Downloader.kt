package io.github.cs2026g1.mdds.modelmanager

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

object Downloader {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, unit = TimeUnit.SECONDS)
        .readTimeout(60, unit = TimeUnit.SECONDS)
        .build()
    sealed class DownloadResult {
        data class Success(val file: File, val sha256: String) : DownloadResult()
        data class Failure(val reason: String) : DownloadResult()
    }

    fun download(context: Context, url: String, onProgress: (downloaded: Long, total: Long) -> Unit = {_, _ ->}) : DownloadResult {
        val tmpFile = ModelFileManager.getTmpFile(context)
        val request = Request.Builder().url(url).build()
        return try{
            client.newCall(request).execute().use { response ->
                if(!response.isSuccessful) {
                    return DownloadResult.Failure("Server returned ${response.code}")
                }
                val body = response.body ?: return DownloadResult.Failure("Empty response body")
                val total = body.contentLength()
                FileOutputStream(tmpFile).use { output ->
                    body.byteStream().use { input ->
                        val buffer = ByteArray(8192)
                        var downloaded = 0L
                        var read: Int
                        var lastProgressUpdateMS = 0L

                        while(input.read(buffer).also {read = it} != -1) {
                            output.write(buffer, 0, read)
                            downloaded += read

                            val now = System.currentTimeMillis()
                            if (now - lastProgressUpdateMS >= 250){
                                lastProgressUpdateMS = now
                                onProgress(downloaded, total)
                            }
                        }
                        onProgress(downloaded, total)


                    }
                }
                if (total > 0 && tmpFile.length() != total) {
                    tmpFile.delete()
                    return DownloadResult.Failure("Download was incomplete")
                }
                if (tmpFile.length() == 0L) {
                    tmpFile.delete()
                    return DownloadResult.Failure("Downloaded file is empty")
                }
                DownloadResult.Success(tmpFile, ModelFileManager.sha256(tmpFile))

            }
        } catch (e: Exception) {
            tmpFile.delete()
            DownloadResult.Failure(e.message ?: "Network error")
        }
    }
    fun commit(context: Context, tmpFile: File, hash: String, version: String): Boolean {
        val modelFile = ModelFileManager.getModelFile(context)
        return try{
            tmpFile.copyTo(modelFile, overwrite = true)
            tmpFile.delete()

            if(modelFile.exists() && modelFile.length() > 0) {
                Prefs.setModelHash(context, hash, version)
                true
            }
            else{
                false
            }
        }
        catch (e: Exception) {
            false
        }

    }
}