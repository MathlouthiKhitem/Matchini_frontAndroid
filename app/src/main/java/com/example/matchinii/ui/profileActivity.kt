package com.example.matchinii.ui
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.matchinii.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
private var loginIntent2: String? = null
lateinit var editProfile : FloatingActionButton
private var appbar : BottomAppBar? = null
private var nameintent: String? = null
private var ageintent: String? = null
private var imageintent: String? = null
lateinit var nameTextView : TextView
lateinit var ageTextView : TextView
lateinit var imageView : ImageView
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        appbar = findViewById(R.id.bottomAppBar2)
        nameTextView = findViewById(R.id.textView6)
        ageTextView = findViewById(R.id.textView7)
        imageView = findViewById(R.id.imageView)
        loginIntent2 = intent.getStringExtra("login")
        nameintent = intent.getStringExtra("name")
        ageintent = intent.getStringExtra("age")
        imageintent = intent.getStringExtra("image")
        editProfile = findViewById(R.id.floatingActionButton)
        editProfile.setOnClickListener(){
            startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java).apply {
               putExtra("login" , loginIntent2)
            Log.e("intent 2 : " , loginIntent2.toString())
            })
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val name = intent.getStringExtra("name")
                println("name:$name")
                nameTextView.text = name
            }
        }
    }
}