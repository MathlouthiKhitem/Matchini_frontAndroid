package com.example.matchinii.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.models.data
import com.example.matchinii.viewmodels.ApiInterface
import kotlinx.android.synthetic.main.card_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MyAdapter(private val context: HomeActivity, private val myUserArray: ArrayList<User> ) :PagerAdapter() {

    override fun getCount(): Int {
        return myUserArray.size
         }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
       }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item , container , false )
        val model = myUserArray[position]
        val image = model.Image
       val login = model.login
        val firstname = model.FirstName
        val age = model.Age
        val uri = Uri.parse(image)
        val like = view.imageView2
        view.textView3.text = age.toString();
        view.textView8.text = firstname
        view.cardimage.setImageURI(uri)
        Log.e("yoyoyoyoyoyoyoyo" ,age.toString())
        Log.e("yoyoyoyoyoyoyoyo" ,firstname)
        Log.e("yoyoyoyoyoyoyoyo" ,uri.toString())
        view.setOnClickListener(object : HomeActivity.DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
              val  loginIntent= context.intent.getStringExtra("login" )
                var apiservicTest = ApiInterface.create()
                like.visibility = View.VISIBLE
                Handler().postDelayed({like.visibility =View.INVISIBLE }, 1000)
                val map: HashMap<String, String> = HashMap()
                map["login"] = login
                Log.e("jjjjjjjjjjj", login)
                Log.e("jjjjjjjjjjj", map.toString())
                apiservicTest.getObjectId(map)
                    .enqueue(object : Callback<data> {
                        override fun onResponse(call: Call<data>, response: Response<data>) {
                            println("value")
                            println(response.body()?.value)
                            if (response.body()!= null)
                            { val bundle = Bundle();
                                bundle.putString("lena ",response.body()?.value )
                                map["login"] =  loginIntent.toString()
                                map["Matches"] = response.body()?.value.toString()
                                Log.e("///////////" , loginIntent.toString())
                                apiservicTest.addMatches(map).enqueue(object : Callback<User>
                                {
                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {
                                        val user = response.body()
                                        println("value")
                                              }
                                    override fun onFailure(
                                        call: Call<User>,
                                        t: Throwable
                                    ) {
                                        print("you are a failure ")
                                    }
                                })
                            }
                        }
                            override fun onFailure(call: Call<data>, t: Throwable) {
                                Log.e("you are\",\"a failure",t.toString())
                   }
               })

            }

        })
        container.addView(view , position)
        return  view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
       container.removeView(`object` as View)
    }
}


