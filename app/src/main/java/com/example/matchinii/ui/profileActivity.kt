package com.example.matchinii.ui
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.viewmodels.ApiInterface
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var loginIntent2: String? = null
lateinit var editProfile : FloatingActionButton
private var appbar : BottomAppBar? = null
lateinit var nameTextView : TextView
lateinit var ageTextView : TextView
lateinit var imageView : ImageView
private var loginIntentEdit: String? = null
var apiservice2= ApiInterface.create()
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        appbar = findViewById(R.id.bottomAppBar2)
        nameTextView = findViewById(R.id.textView6)
        ageTextView = findViewById(R.id.textView7)
        imageView = findViewById(R.id.imageView)
        loginIntent2 = intent.getStringExtra("login")
        loginIntentEdit = intent.getStringExtra("login1")
    editProfile = findViewById(R.id.floatingActionButton)
        val map: HashMap<String, String> = HashMap()
        map["login"] = loginIntent2.toString()
        map["login1"] = loginIntentEdit.toString()
        apiservice2.getConnectedUser(map).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                Log.e("yyyyyyyy" ,loginIntentEdit.toString() )
                val user = response.body()
                for( i in user!!.indices ) {
                   nameTextView.text = user?.get(i)?.FirstName
                    ageTextView.text = user.get(i)?.Age.toString()!!
                    imageView.setImageURI(Uri.parse(user?.get(i)?.Image))
                }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Toast.makeText(this@ProfileActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
            }
        })
        editProfile.setOnClickListener(){
            startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java).apply {
               putExtra("login" , loginIntent2)

            Log.e("intent 2 : " , loginIntent2.toString())

            })
        }
    }

}