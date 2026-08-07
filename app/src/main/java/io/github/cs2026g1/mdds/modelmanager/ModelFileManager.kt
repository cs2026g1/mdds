package io.github.cs2026g1.mdds.modelmanager

import android.content.Context
import java.io.File
import java.security.MessageDigest

object ModelFileManager {
    private const val MODEL_FILE_NAME = "model.tflite"
    private const val TMP_FILE_NAME = "model.download.tmp"

    fun getModelFile(context: Context): File = File(context.filesDir, MODEL_FILE_NAME)
    fun getTmpFile(context: Context): File = File(context.filesDir, TMP_FILE_NAME)

    fun modelExists(context: Context): Boolean{
        val file = getModelFile(context)
        return file.exists() && file.length() > 0
    }

    fun sha256(file: File): String{
        val digest = MessageDigest.getInstance("SHA-256")
        file.inputStream().use { input ->
            val buffer = ByteArray(8192)
            while(true){
                val bytesRead = input.read(buffer)
                if (bytesRead == -1) break
                digest.update(buffer, 0, bytesRead)
            }

        }
        return digest.digest().joinToString("") {"%02x".format(it)}
    }


}