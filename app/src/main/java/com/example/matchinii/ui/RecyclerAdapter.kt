package com.example.matchinii.ui



import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matchinii.R
import com.example.matchinii.models.User
import  com.example.matchinii.ui.ViewHodler


class RecyclerAdapter (private val UserList: ArrayList<User>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item , parent , false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
       holder.itemName.text = UserList[position].FirstName
        holder.itemImage.setImageResource(R.drawable.user)
    }

    override fun getItemCount(): Int {
       return  UserList.size
    }
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var itemImage : ImageView
        var itemName : TextView
        init {
            itemImage = itemView.findViewById<ImageView>(R.id.userProfileImage)
            itemName = itemView.findViewById<TextView>(R.id.textView9)

        }
    }


}