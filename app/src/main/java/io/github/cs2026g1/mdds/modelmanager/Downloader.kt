package io.github.cs2026g1.mdds.modelmanager

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileInputStream
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
        return try{

        }
        catch (e: Exception) {

        }

    }
}