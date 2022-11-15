package com.example.matchinii.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.example.matchinii.R
import com.google.android.material.bottomappbar.BottomAppBar


lateinit var login : TextView
lateinit var pwd : TextView
lateinit var logout : Button
private var appbar : BottomAppBar? = null
private var loginIntent: String? = null

private lateinit var mSharedPref: SharedPreferences
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        login = findViewById(R.id.textView2)
        pwd = findViewById(R.id.textView3)

       appbar = findViewById(R.id.bottomAppBar2)

        logout = findViewById(R.id.logout)
        loginIntent = intent.getStringExtra("login")
        mSharedPref = getSharedPreferences("PREF_NAME", MODE_PRIVATE);
        login.text= mSharedPref.getString("login","").toString()
        pwd.text = mSharedPref.getString("password","").toString()
        logout.setOnClickListener() {
            getSharedPreferences("PREF_NAME", MODE_PRIVATE).edit().clear().apply()
            finish()
            startActivity(Intent(this@HomeActivity, LogInActivity::class.java))

        }
        appbar!!.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {

                    startActivity(Intent(this@HomeActivity, ProfileActivity::class.java).apply {
                        putExtra("login" , loginIntent)
                        Log.e("intent1", loginIntent.toString())
                    })

                    true
                }
                R.id.messages -> {
                    Toast.makeText(this,"messahes",Toast.LENGTH_LONG).show()
                    true
                }
                R.id.search -> {
                    Toast.makeText(this,"search",Toast.LENGTH_LONG).show()
                    true
                }
                R.id.home -> {
                    startActivity(Intent(this@HomeActivity, EditProfileActivity ::class.java).apply {
                        putExtra("login" , loginIntent)
                    })

                    true
                }

                else -> false
            }

        }

    }

   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_item_one-> {

               Toast.makeText(this,"yohohoho",Toast.LENGTH_LONG).show()


                return true
            }
            R.id.action_item_two -> {
                Toast.makeText(this,"hihihi",Toast.LENGTH_LONG).show()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    */
}