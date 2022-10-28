package com.ansh.githubissues

import android.os.Bundle
import android.widget.SearchView
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
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }
        })


        //fetching data from Api
        val issuesApi = RetrofitHelper.getInstance().create(GithubApi::class.java)
        GlobalScope.launch {
            val result = issuesApi.getIssues("closed", 2)
            if (result != null) {

                val issuesList = result.body()
                issuesList?.forEach {
                    list.add(
                        IssuesModel(
                            it.title,
                            it.created_at,
                            it.closed_at,
                            it.user.login,
                            it.user.avatar_url
                        )
                    )
                }
            }
        }.invokeOnCompletion {
            runOnUiThread {
                setUpRecyclerView()
            }

        }
    }

    private fun filterList(filteredText:String) {
        val filteredList = ArrayList<IssuesModel>()
        // filtering based on search input
        for (item:IssuesModel in list) {
            if (item.title.lowercase().contains((filteredText.lowercase()))) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            //sending empty list
            adapter.setFilteredList(ArrayList())
            binding.tvRepoName.text = "No Issues found with given input"
        } else {
            //sending filtered list
            binding.tvRepoName.text = getString(R.string.search_results_description_text)
            adapter.setFilteredList(filteredList)
        }

    }

    private fun setUpRecyclerView() {

        adapter = RvIssuesAdapter(this, list)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

        })

    }
}