package com.example.matchinii.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchinii.R
import com.example.matchinii.models.User
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private var firstName :String = ""
private var image :String = ""
class ChatActivity : AppCompatActivity() {
    lateinit var recylcerViewAdapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

      val   loginIntent = intent.getStringExtra("login")
        val map: HashMap<String, String> = HashMap()
        var userList : MutableList<User> = ArrayList()
        map["login"] = loginIntent.toString()

        apiservice.getUser(map).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) { val user = response.body()
                for( i in user!!.indices ) {
                    firstName = user?.get(i)?.FirstName.toString()
                   image = user?.get(i)?.Image.toString()
                   userList = ArrayList()
                    userList.addAll(user)
                    Log.e("yoyoyoyoyoy" ,  userList.toString())
                    recylcerViewAdapter = RecyclerAdapter(userList)

                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
               Log.e("yoyoyoy" , "Failure")
            }
        })

    }
}