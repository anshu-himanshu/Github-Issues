package com.ansh.githubissues

import modelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/issues")
    suspend fun getIssues(@Query("state")state:String):Response<modelItem>
}