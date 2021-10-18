package com.android.driveit.data.source.network

import com.squareup.moshi.Json

data class LoginResponse(val token: String)

data class PhotoResult(val results: List<Photo>)

data class Photo(val urls: Urls)

data class Urls(val small: String)

data class UserInfo(val data: UserData)

data class UserData(
    val id: Int,
    val email: String,

    @Json(name = "first_name")
    val firstName: String,

    @Json(name = "last_name")
    val lastName: String,

    val avatar: String
)

data class Car(val name: String, val price: Int, val url: String, val duration: String)