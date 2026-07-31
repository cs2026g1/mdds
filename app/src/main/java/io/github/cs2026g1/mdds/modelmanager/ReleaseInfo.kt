package io.github.cs2026g1.mdds.modelmanager

data class ReleaseInfo (
    val version: String,
    val name: String,
    val downloadUrl: String?, // can be null
    val body: String


)