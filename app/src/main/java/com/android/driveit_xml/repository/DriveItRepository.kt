package com.android.driveit_xml.repository

import com.android.driveit_xml.data.source.network.Reqres

class DriveItRepository {

    val reqresService = Reqres.reqresService

    suspend fun loginUser(email: String, password: String) =
        reqresService.loginUser(email, password)

    suspend fun getCarImages() = reqresService.getCarImages()
}