package com.ansh.githubissues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ansh.githubissues.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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