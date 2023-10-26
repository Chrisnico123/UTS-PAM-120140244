package com.dicoding.utspam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.utspam.R
import com.example.utspam.model.DataItem

class UserAdapter(private val users: MutableList<DataItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private var onUserItemClickListener: OnUserItemClickListener? = null

    interface OnUserItemClickListener {
        fun onUserItemClicked(dataItem: DataItem)
    }

    fun setOnUserItemClickListener(listener: OnUserItemClickListener) {
        this.onUserItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ListViewHolder(
            view
        )
    }

    fun addUser(newUsers: DataItem) {
        users.add(newUsers)
        notifyItemInserted(users.lastIndex)
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = users[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80).placeholder(R.drawable.iconsplash))
            .transform(CircleCrop())
            .into(holder.ivAvatar)

        holder.tvUserName.text = "${user.firstName} ${user.lastName}"
        holder.tvEmail.text = user.email
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName: TextView = itemView.findViewById(R.id.item_name)
        var tvEmail: TextView = itemView.findViewById(R.id.item_email)
        var ivAvatar: ImageView = itemView.findViewById(R.id.item_image)

    }
}