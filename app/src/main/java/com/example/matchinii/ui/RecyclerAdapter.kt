package com.example.matchinii.ui




import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.models.data
import kotlinx.android.synthetic.main.card_item.view.*


class RecyclerAdapter (private val UserList: ArrayList<User>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item , parent , false)
        return  ViewHolder(v)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
            var name =  UserList[position].login.substringBefore('@')
       holder.itemName.text = name
        var image = UserList[position].Image
        Glide.with(holder.itemImage.context).load(image).into(holder.itemImage)
        Log.e("tttttttt" , UserList.toString())
//        Log.e("tttttttt" , UserList[position].login)


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