package com.example.matchinii.ui.Activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.api.ApiInterface
import com.google.android.material.bottomappbar.BottomAppBar
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private var loginIntent2: String? = null
lateinit var nameTextView : TextView
private var appbar : BottomAppBar? = null
lateinit var imageView : CircleImageView
private var loginIntentEdit: String? = null

private var imageIntent: String? = null
private var ageIntent: String? = null
var apiservice2= ApiInterface.create()
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        appbar = findViewById(R.id.bottomAppBar2)
        ageIntent = intent.getStringExtra("age")
        imageIntent = intent.getStringExtra("image")
        val mPulsator: PulsatorLayout = findViewById(R.id.pulsator)
        mPulsator.start()
        imageView = findViewById(R.id.circle_profile_image)
        nameTextView = findViewById(R.id.profile_name)
        val edit_btn = findViewById<ImageButton>(R.id.edit_profile)
        edit_btn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java).putExtra("login" , loginIntent2)
            startActivity(intent)
        }
        val settings = findViewById<ImageButton>(R.id.settings)
        settings.setOnClickListener {
            val intent = Intent(this@ProfileActivity, SettingsActivity::class.java).putExtra("login" , loginIntent2)
            startActivity(intent)
        }

        loginIntent2 = intent.getStringExtra("login")
        loginIntentEdit = intent.getStringExtra("login")
        val map: HashMap<String, String> = HashMap()
        map["login"] = loginIntent2.toString()
        Log.e("intent de login : " , loginIntent2.toString())
        apiservice2.getOne(map).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                val substring = user?.login!!.substringBefore("@")
                nameTextView.text = substring
                Glide.with(this@ProfileActivity).load(user?.Image).into(imageView)
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }

        })
        appbar!!.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    startActivity(Intent(this@ProfileActivity, ProfileActivity::class.java).apply {
                        putExtra("login" , loginIntent2 )
                        putExtra("age" , ageIntent)
                        putExtra("image" , imageIntent)
                    })
                    true
                }
                R.id.messages -> {
                    startActivity(Intent(this@ProfileActivity, ChatActivity::class.java).apply {
                        putExtra("login" , loginIntent2 )
                        /*   putExtra("id final", idFinal.toString())
                          Log.e("id1intent",id1.toString())
                          Log.e("id2intent",id2.toString())*/

                    })
                    true
                }

                R.id.home -> {
                    startActivity(Intent(this@ProfileActivity, MapActivity ::class.java).apply {
                        putExtra("login" , loginIntent2 )
                    })
                    true
                }
                else -> false
            }
        }

    }

    }

