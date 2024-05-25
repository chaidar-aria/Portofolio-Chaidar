package com.chaidar.githubuserportofolio.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaidar.githubuserportofolio.R
import com.chaidar.githubuserportofolio.data.response.FollowersResponseItem

class FollowersAdapter(private var followersList: List<FollowersResponseItem>) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = followersList[position]
        holder.bind(follower)
    }

    override fun getItemCount(): Int {
        return followersList.size
    }

    fun updateData(newFollowersList: List<FollowersResponseItem>) {
        followersList = newFollowersList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val loginTextView: TextView = itemView.findViewById(R.id.item_name_rick)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.item_image_Rick)
        private val urlTextView: TextView = itemView.findViewById(R.id.item_species_rick)

        fun bind(follower: FollowersResponseItem) {
            loginTextView.text = "Username: ${follower.login ?: "N/A"}"
            urlTextView.text = follower.url

            // Load avatar image using a library like Glide or Picasso
            Glide.with(itemView.context)
                .load(follower.avatarUrl)
                .placeholder(R.drawable.ic_launcher_background) // You can set a placeholder image
                .error(R.drawable.ic_launcher_background) // You can set an error image
                .into(avatarImageView)
        }
    }
}