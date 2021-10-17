package com.android.driveit.repository

import com.android.driveit.data.source.network.Reqres

class DriveItRepository {

    val reqresService = Reqres.reqresService

    suspend fun loginUser(email: String, password: String) =
        reqresService.loginUser(email, password)

    suspend fun getCarImages() = reqresService.getCarImages()
}