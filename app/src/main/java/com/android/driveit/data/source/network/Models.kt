package com.android.driveit.data.source.network

data class LoginResponse(val token: String)

data class PhotoResult(val results: List<Photo>)

data class Photo(val urls: Urls)

data class Urls(val small: String)