package com.chaidar.githubuserportofolio.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaidar.githubuserportofolio.R
import com.chaidar.githubuserportofolio.data.response.FollowersResponseItem
import com.chaidar.githubuserportofolio.data.retrofit.ApiConfig
import com.chaidar.githubuserportofolio.ui.adapter.FollowersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {

    private var username: String? = null
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString("keyData")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_follower, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        val followersRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFollowers)
        followersAdapter = FollowersAdapter(emptyList())

        followersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = followersAdapter
        }

        getFollowersData()

        return view
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getFollowersData() {
        showLoading(true)

        val usernameOrDefault = username ?: "DefaultUsername"
        val call: Call<List<FollowersResponseItem>> =
            ApiConfig.getService().getFollowers(usernameOrDefault)

        call.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                showLoading(false)

                if (response.isSuccessful) {
                    val followersList: List<FollowersResponseItem>? = response.body()
                    followersAdapter.updateData(followersList ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                // Handle error
                showLoading(false)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString("keyData", username)
                }
            }
    }
}