package com.chaidar.githubuserportofolio.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chaidar.githubuserportofolio.R
import com.chaidar.githubuserportofolio.data.response.ResultsItem
import com.chaidar.githubuserportofolio.ui.detail.DetailUserActivity

class GithubAdapter(private val context: Context, val dataRick: List<ResultsItem?>?) :
    RecyclerView.Adapter<GithubAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgRick = view.findViewById<ImageView>(R.id.item_image_Rick)
        val nameRick = view.findViewById<TextView>(R.id.item_name_rick)
        val loginRick = view.findViewById<TextView>(R.id.item_species_rick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataRick?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameRick.text = dataRick?.get(position)?.login
        holder.loginRick.text = dataRick?.get(position)?.url

        Glide.with(holder.imgRick).load(dataRick?.get(position)?.avatarUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgRick)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra("login", dataRick?.get(position)?.login)
            context.startActivity(intent)
        }
    }
}