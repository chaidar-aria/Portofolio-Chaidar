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
import com.chaidar.githubuserportofolio.data.response.FollowingResponseItem
import com.chaidar.githubuserportofolio.data.retrofit.ApiConfig
import com.chaidar.githubuserportofolio.ui.adapter.FollowingAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {

    private var username: String? = null
    private lateinit var followingAdapter: FollowingAdapter
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
        val view = inflater.inflate(R.layout.fragment_following, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        val followingRecyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFollowing)
        followingAdapter = FollowingAdapter(emptyList())

        followingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = followingAdapter
        }

        getFollowingData()

        return view
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getFollowingData() {
        showLoading(true)

        val usernameOrDefault = username ?: "DefaultUsername"
        val call: Call<List<FollowingResponseItem>> =
            ApiConfig.getService().getFollowing(usernameOrDefault)

        call.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                showLoading(false)

                if (response.isSuccessful) {
                    val followingList: List<FollowingResponseItem>? = response.body()
                    followingAdapter.updateData(followingList ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                showLoading(false)

                // Handle error
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString("keyData", username)
                }
            }
    }
}