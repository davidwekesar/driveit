package com.android.driveit.data.source.network

import com.android.driveit.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://reqres.in/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ReqresService {

    @FormUrlEncoded
    @POST(value = "api/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("api/users/2")
    suspend fun getUserInfo(): UserInfo

    @Headers("Authorization: Client-ID ${BuildConfig.accessKey}")
    @GET("https://api.unsplash.com/search/photos?page=1&query=car")
    suspend fun getCarImages(): PhotoResult
}

object Reqres {
    val reqresService: ReqresService by lazy {
        retrofit.create(ReqresService::class.java)
    }
}