package com.ansh.githubissues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val issuesApi = RetrofitHelper.getInstance().create(GithubApi::class.java)
        GlobalScope.launch {
            val result = issuesApi.getIssues("closed",2)
            if (result!=null){


                val issuesList =  result.body()
                issuesList?.forEach {
                    Log.e("ISSUES",it.title)
                }

            }
        }
    }
}