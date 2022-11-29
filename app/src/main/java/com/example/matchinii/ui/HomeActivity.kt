package com.example.matchinii.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager

import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.viewmodels.ApiInterface
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.card_item.*
import kotlinx.android.synthetic.main.card_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



lateinit var login : TextView
lateinit var pwd : TextView

private var appbar : BottomAppBar? = null
private var loginIntent: String? = null
private var imageIntent: String? = null
private var ageIntent: String? = null
lateinit var  myUserList : ArrayList<User>



//private var loginin : String? = ""
//private var password : String? = ""
//private var lastname: String? = ""
//private var numero : String? = ""
private var firstName : String? = ""
//private var sexein : String? = ""
private var Age :Int =0
private var image :String = ""
//private var uri : Uri?= null

var apiservice= ApiInterface.create()
private lateinit var mSharedPref: SharedPreferences
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        appbar = findViewById(R.id.bottomAppBar2)
       // myCard = findViewById(R.id.cardview)
        //  logout = findViewById(R.id.logout)
        loginIntent = intent.getStringExtra("login")
        ageIntent = intent.getStringExtra("age")
        imageIntent = intent.getStringExtra("image")
        // editProfile = findViewById(R.id.floatingActionButton)
        //    editProfile.setOnClickListener(){}
      /*  viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(viewPager.currentItem == 0) {

                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        val map: HashMap<String, String> = HashMap()
        map["login"] = loginIntent.toString()

        apiservice.getUser(map).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) { val user = response.body()

               for( i in user!!.indices ) {
                    firstName = user?.get(i)?.FirstName
                    Age = user.get(i)?.Age!!
                    image = user?.get(i)?.Image.toString()
                    myUserList = ArrayList()
                   myUserList.addAll(user)
                           val myAdapter = MyAdapter(
                        this@HomeActivity, myUserList)
                    Log.e("i =  ", i.toString())
                           Log.e("contenu de liste =  ", myUserList.toString())
                    Log.e("taille de docs  ",user!!.indices.toString())
                        Log.e("size de list = ", myUserList.size.toString())
                        viewPager.adapter = myAdapter


                }
                if (user != null) {

                } else {
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }
        })*/



           // myUserList = ArrayList()
    //    Log.e("hererererer", firstName.toString())
     //  Log.e("hererererer", image.toString())
     //   myUserList.add(User("" ,"", firstName!!, "", Age as Int , "","" , image ))
       //  val  myAdapter = MyAdapter(this , myUserList)

      //  Log.e("here", myUserList.toString())
        //    viewPager.adapter = myAdapter
        //    mSharedPref = getSharedPreferences("PREF_NAME", MODE_PRIVATE);
    //    login.text= mSharedPref.getString("login","").toString()
   //     pwd.text = mSharedPref.getString("password","").toString()
       // logout.setOnClickListener() {
        //    getSharedPreferences("PREF_NAME", MODE_PRIVATE).edit().clear().apply()
       //     finish()
       //     startActivity(Intent(this@HomeActivity, LogInActivity::class.java))
    //    }
        appbar!!.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    startActivity(Intent(this@HomeActivity, ProfileActivity::class.java).apply {
                        putExtra("login" , loginIntent)
                            putExtra("age" , ageIntent)
                            putExtra("image" , imageIntent)

                               })
                    true
                }
                R.id.messages -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, ChatFragment())
                        .commit()
                    true
                }
                R.id.search -> {
                    Toast.makeText(this,"search",Toast.LENGTH_LONG).show()
                    true
                }
                R.id.home -> {
                    startActivity(Intent(this@HomeActivity, MapsActivity ::class.java).apply {
                        putExtra("login" , loginIntent)
                    })
                    true
                }
                else -> false
            }
        }

    }
   /* private fun getItem(i : Int ): Int {
        return viewPager.currentItem +i ;
    }*/
    abstract class DoubleClickListener : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(v: View?) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v)
            }
            lastClickTime = clickTime
        }

        abstract fun onDoubleClick(v: View?)

        companion object {
            private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
        }
    }
}