package com.example.matchinii.ui
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.viewmodels.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
lateinit var LastName: TextInputEditText
lateinit var LastNameLayout: TextInputLayout
lateinit var FirstName: TextInputEditText
lateinit var FirstNameLayout: TextInputLayout
lateinit var age: TextInputEditText
lateinit var ageLayout: TextInputLayout
lateinit var phone: TextInputEditText
lateinit var phoneLayout: TextInputLayout
lateinit var btnNext: Button
lateinit var Profile: ImageView
lateinit var btnMale: ImageButton
lateinit var btnFemale: ImageButton
private var loginIntent3 : String? = null

lateinit var sexe : String
private var selectedImageUri: Uri? = null

class EditProfileActivity : AppCompatActivity() {
    private val startForResultOpenGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data!!.data
                Profile!!.setImageURI(selectedImageUri)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        LastName = findViewById(R.id.EditTextLastName)
        LastNameLayout = findViewById(R.id.TextInputLayoutLastName)
        FirstName = findViewById(R.id.EditLayoutFirstName)
        FirstNameLayout = findViewById(R.id.TextInputLayoutFirstName)
        age = findViewById(R.id.EditTextAge)
        ageLayout = findViewById(R.id.TextInputLayoutAge)
        phone = findViewById(R.id.EditTextPhone)
        phoneLayout = findViewById(R.id.TextInputLayoutNumber)
        btnNext = findViewById(R.id.Save)
        Profile = findViewById(R.id.profilepic)
       btnMale = findViewById(R.id.Male)
        btnFemale = findViewById(R.id.Female)

        btnMale.setOnClickListener(){
            sexe = "male"
           Toast.makeText(this,"male",Toast.LENGTH_LONG).show()
            btnMale.setBackgroundColor(Color.GRAY);
        }
        btnFemale.setOnClickListener(){
            sexe = "female"
            Toast.makeText(this,"female",Toast.LENGTH_LONG).show()
            btnFemale.setBackgroundColor(Color.GRAY);
        }
        Profile!!.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            startForResultOpenGallery.launch(intent)
            Log.e("image", selectedImageUri.toString())
        }
        loginIntent3 = intent!!.getStringExtra("login" )
        var services = ApiInterface.create()
        btnNext.setOnClickListener(){
            val map: HashMap<String, String> = HashMap()
            map["login"] = loginIntent3.toString()
            map["FirstName"] = FirstName.text.toString()
            map["LasteName"] = LastName.text.toString()
            map["Age"] = age.text.toString()
            map["Numero"] = phone.text.toString()
            map["Sexe"] = sexe
            map["Image"] = selectedImageUri.toString()
            Log.e("intent3" ,loginIntent3.toString())
            Log.e("lmap " ,map.toString())
            services.modifier(map).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val user = response.body()

                    Log.e("user is " , user.toString())

                    /*        if (remember.isChecked) {
                        mSharedPref.edit().apply {
                            putBoolean("IS_REMEMBRED", true)
                            putString("login", txtLoginin.text.toString())
                            putString("password", txtPasswordin.text.toString())
                        }.apply()
                    } else {
                        mSharedPref.edit().clear().apply()
                    }
                    startActivity(Intent(this@EditProfileActivity, HomeActivity::class.java))
                    Log.e("jjjjjjjj", response.body().toString())
 */
                    if (user != null) {
                        Toast.makeText(this@EditProfileActivity, " Success", Toast.LENGTH_SHORT)
                            .show()
                        startActivity (Intent(this@EditProfileActivity, ProfileActivity::class.java).apply {
                            putExtra("name" , FirstName.text.toString() )
                            putExtra("age" , age.text.toString())
                        putExtra("image" , selectedImageUri.toString())

                       })
                   //
                      //  val intent = Intent()
                      //  intent.putExtra("name", FirstName.text.toString())
                      //  setResult(Activity.RESULT_OK, intent)
                      //  finish()
                    } else {
                        Toast.makeText(
                            this@EditProfileActivity,
                            "User not found",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@EditProfileActivity, "Connexion error!", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}
