package com.example.matchinii.ui



import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matchinii.R
import com.example.matchinii.models.User


class RecyclerAdapter(private val userList: MutableList<User>) : RecyclerView.Adapter<ViewHodler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodler {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)

        return ViewHodler(view)
    }

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {

        val name = userList[position].FirstName
        val image = userList[position].Image

        holder.userPic.setImageURI(Uri.parse(userList[position].Image))
        holder.userName.text = name


        holder.itemView.setOnClickListener{

        }

    }
    override fun getItemCount() = userList.size

}