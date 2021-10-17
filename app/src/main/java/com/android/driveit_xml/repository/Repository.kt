package com.android.driveit_xml.repository

import com.android.driveit_xml.data.source.network.Reqres

class Repository {

    val reqresService = Reqres.reqresService

    suspend fun loginUser(email: String, password: String) =
        reqresService.loginUser(email, password)
}