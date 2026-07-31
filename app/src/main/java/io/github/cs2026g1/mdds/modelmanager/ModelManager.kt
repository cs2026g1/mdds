package io.github.cs2026g1.mdds.modelmanager
import android.content.Context
import android.app.Activity

class ModelManager(private val context: Context) {
    fun checkLatest() {
        Thread {
            val latest = CheckLatest().check()
            (context as Activity).runOnUiThread {
                if (latest != null) {
                    Toasts.show(context, "Latest: ${latest.version}")
                } else {
                    Toasts.show(context, "Failed to check updates.")
                }
            }
        }.start()
    }
}