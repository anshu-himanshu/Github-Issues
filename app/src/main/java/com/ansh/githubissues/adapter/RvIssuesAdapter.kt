package com.ansh.githubissues.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ansh.githubissues.R
import com.ansh.githubissues.models.IssuesModel
import com.bumptech.glide.Glide

class RvIssuesAdapter(val context: Context, var issuesList:ArrayList<IssuesModel>):
    RecyclerView.Adapter<RvIssuesAdapter.ItemViewHolder>() {


 inner   class ItemViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
     val userImage:ImageView = itemView.findViewById(R.id.ivUserImage)
     val userName: TextView = itemView.findViewById(R.id.tvUserName)
     val issueTitle: TextView = itemView.findViewById(R.id.tvIssueTitle)
     val userCreatedDate: TextView = itemView.findViewById(R.id.tvIssueCreatedDate)
     val userClosedDate: TextView = itemView.findViewById(R.id.tvIssueClosedDate)



    }

    fun setFilteredList(filteredList:ArrayList<IssuesModel>){
        this.issuesList = filteredList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_issues_item, parent, false)

        return ItemViewHolder(view)    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.issueTitle.text = issuesList[position].title
        holder.userClosedDate.text ="Closed On: "+ issuesList[position].closedDate
        holder.userCreatedDate.text ="Created On: "+ issuesList[position].createdDate
        holder.userName.text ="User Name: "+ issuesList[position].userName
        Glide.with(context).load(issuesList[position].userImageUrl).into(holder.userImage)
    }

    override fun getItemCount(): Int {
        return  issuesList.size
    }
}