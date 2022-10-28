package com.ansh.githubissues

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ansh.githubissues.adapter.RvIssuesAdapter

import com.ansh.githubissues.databinding.ActivityMainBinding
import com.ansh.githubissues.models.IssuesModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private val list = ArrayList<IssuesModel>()
    private lateinit var adapter: RvIssuesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialing Views
        searchView = binding.searchView
        recyclerView = binding.rvIssuesList


        //handling SearchView
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

            fun callSearch(query: String?) {
                //Do searching
            }
        })


        val issuesApi = RetrofitHelper.getInstance().create(GithubApi::class.java)
        GlobalScope.launch {
            val result = issuesApi.getIssues("closed", 2)

            if (result != null) {

                val issuesList = result.body()
                issuesList?.forEach {
                    val item = IssuesModel(
                        it.title,
                        it.created_at,
                        it.closed_at,
                        it.user.login,
                        it.user.avatar_url
                    )
                    Log.e(
                        "ISSUES",
                        it.title + " " + it.user.avatar_url + " " + it.user.login + " " + it.closed_at + " " + it.created_at + " "
                    )
                    list.add(item)
                }

            }
        }.invokeOnCompletion {
            runOnUiThread {
                setUpRecyclerView()
            }

        }
    }

    private fun filterList(newText: String) {
        val filteredList = ArrayList<IssuesModel>()

        for (item: IssuesModel in list) {
            if (item.title.toLowerCase().contains((newText.toLowerCase()))) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
        } else {

            adapter.setFilteredList(filteredList)
        }

    }

    private fun setUpRecyclerView() {

        adapter = RvIssuesAdapter(this, list)

        recyclerView.adapter = adapter

    }
}