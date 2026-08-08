package io.github.cs2026g1.mdds.modelmanager
import android.content.Context

object Prefs {
    private const val PREFS_NAME = "mdds_model_pref"
    private const val KEY_MODEL_HASH = "model_sha_256"
    private const val KEY_MODEL_VERSION = "model_version"
    private const val KEY_SKIPPED_VERSION = "skipped_version"

    private fun prefs(context: Context) = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun getModelHash(context: Context): String? = prefs(context).getString(KEY_MODEL_HASH, null)
    fun setModelHash(context: Context, hash: String, version: String) {
        prefs(context).edit()
            .putString(KEY_MODEL_HASH, hash)
            .putString(KEY_MODEL_VERSION, version)
            .apply()
    }
    fun clearModelHash(context: Context) {
        prefs(context).edit()
            .remove(KEY_MODEL_HASH)
            .remove(KEY_MODEL_VERSION)
            .apply()
    }
    fun getCurrentModelVersion(context: Context): String? = prefs(context).getString(KEY_MODEL_VERSION, null)
    fun getSkippedModelVersion(context: Context): String? = prefs(context).getString(KEY_SKIPPED_VERSION, null)
    fun setSkippedVersion(context: Context, version: String) {
        prefs(context).edit()
            .putString(KEY_SKIPPED_VERSION, version)
            .apply()
    }
}