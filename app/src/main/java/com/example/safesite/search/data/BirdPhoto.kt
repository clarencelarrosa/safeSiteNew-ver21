package com.example.safesite.search.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BirdPhoto (
    val id: String,
    val descrip: String?,
    val urls: UnsplashImageUrls,
    val likes: Int,
    val user: UnsplashUsers
): Parcelable {

    @Parcelize
    data class UnsplashImageUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class UnsplashUsers(
        val name: String,
        val username: String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}