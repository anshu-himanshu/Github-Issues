package com.ansh.githubissues

import com.ansh.githubissues.models.Model
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/repos/bumptech/glide/issues")
    suspend fun getIssues(@Query("state")state:String):Response<Model>
}