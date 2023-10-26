package com.example.utspam.fragment

import DetailFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.parsingjson.network.ApiConfig
import com.dicoding.utspam.UserAdapter
import com.example.utspam.R
import com.example.utspam.model.DataItem
import com.example.utspam.model.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaFragment : Fragment() {
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        adapter = UserAdapter(mutableListOf())

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.setOnUserItemClickListener(object : UserAdapter.OnUserItemClickListener {
            override fun onUserItemClicked(dataItem: DataItem) {
                onUserItemClicked(dataItem)
            }
        })

        getUser()

        return view
    }

    private fun getUser() {
        val client = ApiConfig.getApiService().getListUsers("1")

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<DataItem>
                    for (data in dataArray) {
                        adapter.addUser(data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun onUserItemClicked(dataItem: DataItem) {
        val detailFragment = DetailFragment()
        detailFragment.setDataItem(dataItem)

        // Ganti fragment saat pengguna mengklik item
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.container, detailFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
