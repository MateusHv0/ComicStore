package com.example.comicstore.api

import com.example.comicstore.data.Details
import com.example.comicstore.data.ComicResponse
import com.example.comicstore.data.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ComicService {
    @GET("v1/product/")
    suspend fun getComic(
        @Header("Authorization") token: String
    ) : Response<ComicResponse>

    @GET("v1/details/{comic_id}")
    suspend fun getDetails(
        @Path("comic_id") id: Int?,
        @Header("Authorization") token: String
    ) : Response<Details>

    @GET("v1/images/{comic_id}")
    suspend fun getImages(
        @Path("comic_id") id: Int?,
        @Header("Authorization") token: String
    ) : Response<ImageResponse>
}