package io.github.cs2026g1.mdds.modelmanager

import android.content.Context
import android.widget.Toast

object Toasts {
    fun show(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}