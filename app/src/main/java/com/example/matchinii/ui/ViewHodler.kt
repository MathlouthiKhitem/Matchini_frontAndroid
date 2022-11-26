package com.example.matchinii.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matchinii.R

class ViewHodler (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPic : ImageView
        val userName : TextView
        init {
            userPic = itemView.findViewById<ImageView>(R.id.imageView3)
            userName = itemView.findViewById<TextView>(R.id.textView2)
        }
    }