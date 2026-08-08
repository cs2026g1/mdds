package io.github.cs2026g1.mdds.modelmanager

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class CheckLatest {
    private val client = OkHttpClient()
    fun check() : ReleaseInfo? {
        val request = Request.Builder().url(Config.LATEST_RELEASE_API)
            .header("User-Agent", "mdds-app")
            .header("Accept", "application/vnd.github+json")
            .build() // This is necessary else GitHub will reject our request.
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return null
                }
                val responseBody = response.body?.string() ?: return null

                val json = JSONObject(responseBody)
                val version = json.getString("tag_name")
                val releaseName = json.getString("name")
                val body = json.getString("body")

                var downloadUrl: String? = null
                var expectedSha256: String? = null
                val assets = json.getJSONArray("assets")
                if (assets.length() > 0) {

                    val firstAsset = assets.getJSONObject(0)
                    downloadUrl = firstAsset.getString("browser_download_url")
                    val digest = firstAsset.optString("digest", null)
                    expectedSha256 = digest?.removePrefix("sha256:")
                }
                ReleaseInfo(
                    version = version,
                    name = releaseName,
                    downloadUrl = downloadUrl,
                    body = body,
                    expectedSha256 = expectedSha256
                )

            }


        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}