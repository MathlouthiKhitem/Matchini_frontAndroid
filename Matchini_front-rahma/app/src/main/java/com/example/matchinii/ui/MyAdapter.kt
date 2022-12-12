package com.example.matchinii.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.models.bool
import com.example.matchinii.viewmodels.ApiInterface
import kotlinx.android.synthetic.main.card_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.net.URL


class MyAdapter(private val context: HomeActivity, private val myUserArray: ArrayList<User> ) :PagerAdapter() {
    lateinit var userName1: String;
    override fun getCount(): Int {
        return myUserArray.size
         }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
       }
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item , container , false )
        val model = myUserArray[position]
        val image = model.Image
       val login = model.login
        val firstname = model.FirstName
        val age = model.Age
        val url = URL(image)
        val like = view.imageView2
        view.textView3.text = age.toString();
        view.textView8.text = firstname
          Glide.with(context).load(image).into(view.cardimage)
        view.setOnClickListener(object : HomeActivity.DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                view.isClickable = false
              var loginIntent= context.intent.getStringExtra("login" )
                var apiservicTest = ApiInterface.create()
                like.visibility = View.VISIBLE
                Handler().postDelayed({like.visibility =View.INVISIBLE }, 1000)
                val map2: HashMap<String, String> = HashMap()
                map2["login"] = model.login
                Log.e("+++++++++" , loginIntent.toString())
                Log.e("+++++++++" , map2.toString())
                apiservicTest.IsMatched(loginIntent.toString(),map2)
                    .enqueue(object : Callback<bool>{
                        override fun onResponse(
                            call: Call<bool>,
                            response: Response<bool>
                        ) {
                            Log.e("+++++++++" , response.body()?.value.toString())
                            if (response.body()?.value == true)
                            {}
                            view.foreground = context.getDrawable(R.drawable.brown_and_white__modern_travel_app_user_interface__2___1_)
                            Handler().postDelayed({view.foreground =context.getDrawable(R.color.transparent)}, 2000)

                                val myIntent: Intent = Intent(
                                   // view.context, ChatRoomActivity::class.java

                                )
                                 context.intent.putExtra("login",loginIntent)
                                Log.e("login ad",loginIntent.toString())
                               // view.context.startActivity(myIntent)




                        }
                        override fun onFailure(
                            call: Call<bool>,
                            t: Throwable
                        ) {

                        }
                    })

            }

        })
        container.addView(view , position-1)
        Log.e("°°°°index°°°°°°" , (position-1).toString())

        return  view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
       container.removeView(`object` as View)

    }
    fun removeItem(position : Int ) {
        if (position > -1 && position < myUserList.size) {
            myUserList.removeAt(position);
            notifyDataSetChanged();
        }

}
}


