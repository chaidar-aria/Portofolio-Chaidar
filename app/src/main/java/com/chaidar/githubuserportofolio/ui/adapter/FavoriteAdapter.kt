package com.chaidar.githubuserportofolio.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaidar.githubuserportofolio.R
import com.chaidar.githubuserportofolio.database.FavoriteUser
import com.chaidar.githubuserportofolio.databinding.ItemRowFavBinding
import com.chaidar.githubuserportofolio.ui.detail.DetailUserActivity

class FavoriteAdapter(private var dataList: List<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRowFavBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_fav, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to views
        val data = dataList[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .into(holder.binding.itemImageRick)

        holder.binding.itemNameRick.text = data.username
        holder.itemView.setOnClickListener {
            // Meng-handle klik item di sini
            val context = holder.itemView.context
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra("login", data.username)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    // Perbaiki tipe data yang diterima menjadi List<ResultsItem>
    fun updateData(newDataList: List<FavoriteUser>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}