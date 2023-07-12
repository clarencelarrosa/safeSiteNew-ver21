package com.example.safesite.search

import com.example.safesite.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BirdApi {
    companion object {
        const val BASE_URL="https://api.unsplash.com/" //base url link to send the request
        const val CLIENT_ID= "hujCNxi7A11MsIoYd4KWbJspuHMMC3dCHyqCXN1AVWk" //my generated api key in unsplash
    }
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")

    //threading with kotlin coroutines
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResponse
}