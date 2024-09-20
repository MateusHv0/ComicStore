package com.example.comicstore.data
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResponse (
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Image>?
)