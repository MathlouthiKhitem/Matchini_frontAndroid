package com.example.matchinii.ui
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.viewmodels.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.net.URL
import kotlin.math.log

class SecondActivity : AppCompatActivity() {
    lateinit var txtLoginup: TextInputEditText
    lateinit var txtLayoutLoginup: TextInputLayout
    lateinit var txtPasswordup: TextInputEditText
    lateinit var txtLayoutPasswordup: TextInputLayout


    lateinit var register_btn: Button
    lateinit var Profile: ImageView
    lateinit var age: TextInputEditText
    private var selectedImageUri: Uri? = null
    var services = ApiInterface.create()
    private val startForResultOpenGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data!!.data
                Profile!!.setImageURI(selectedImageUri)

            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        txtLoginup = findViewById(R.id.EditEmail)
        txtLayoutLoginup = findViewById(R.id.layoutEmail)
        txtPasswordup = findViewById(R.id.EditPassword)
        txtLayoutPasswordup = findViewById(R.id.LayoutPassword)
        age=findViewById(R.id.edittextage)
        //txtPasswordRep = findViewById(R.id.EditRestPassword)
       // txtLayoutPasswordRep = findViewById(R.id.LayoutRestPassword)

        register_btn = findViewById(R.id.nextbtn)
        Profile = findViewById(R.id.profilepicsignup)
        Profile.setOnClickListener(){
            Profile!!.setOnClickListener{
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*"
                startForResultOpenGallery.launch(intent)
                Log.e("image", selectedImageUri.toString())
            }

        }
        register_btn.setOnClickListener{

                val map: HashMap<String, String> = HashMap()
                map["login"] = txtLoginup.text.toString()
                map["password"] = txtPasswordup.text.toString()
                 map["Image"] = selectedImageUri.toString()
            map["Age"] = age.text.toString()
            Log.e("map",map.toString());
            if (validate()){

            services.register(map).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>){
                    Log.e("//////////////////User ",response.body().toString())
                    val user = response.body()
                    if (user != null)  {
                        val intent = Intent(this@SecondActivity, HomeActivity::class.java).apply {
                            putExtra("login" ,txtLoginup.text.toString() ).apply {
                                putExtra("age" , age.text.toString())
                                putExtra("image" , selectedImageUri.toString())
                            }
                        }
                        startActivity(intent)
                        finish()
                    } else {

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("-----------call",t.toString())
                    Toast.makeText(this@SecondActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                }
            })
            }
        }

    }
    private fun validate(): Boolean {
        txtLayoutLoginup.error = null
        txtLayoutPasswordup.error = null


        if (!Patterns.EMAIL_ADDRESS.matcher(txtLoginup.text.toString()).matches()){
            txtLayoutLoginup.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtPasswordup.text!!.isEmpty()){
            txtLayoutPasswordup.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        return true
    }

    }


