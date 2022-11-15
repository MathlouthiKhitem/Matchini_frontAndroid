package com.example.matchinii.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.viewmodels.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class SecondActivity : AppCompatActivity() {
    lateinit var txtLoginup: TextInputEditText
    lateinit var txtLayoutLoginup: TextInputLayout
    lateinit var txtPasswordup: TextInputEditText
    lateinit var txtLayoutPasswordup: TextInputLayout
    lateinit var txtPasswordRep: TextInputEditText
    lateinit var txtLayoutPasswordRep: TextInputLayout
    lateinit var register_btn: Button
    var services = ApiInterface.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        txtLoginup = findViewById(R.id.EditEmail)
        txtLayoutLoginup = findViewById(R.id.layoutEmail)
        txtPasswordup = findViewById(R.id.EditPassword)
        txtLayoutPasswordup = findViewById(R.id.LayoutPassword)
        txtPasswordRep = findViewById(R.id.EditRestPassword)
        txtLayoutPasswordRep = findViewById(R.id.LayoutRestPassword)
        register_btn = findViewById(R.id.nextbtn)
        register_btn.setOnClickListener{

                val map: HashMap<String, String> = HashMap()
                map["login"] = txtLoginup.text.toString()
                map["password"] = txtPasswordup.text.toString()
            if (validate()&&validate1()){

            services.register(map).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>){
                    Log.e("//////////////////User ",response.body().toString())
                    val user = response.body()
                    if (user != null)  {
                        val intent = Intent(this@SecondActivity, HomeActivity::class.java).apply {
                            putExtra("login" ,txtLoginup.text.toString() )
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
        txtLayoutPasswordRep.error=null

        if (!Patterns.EMAIL_ADDRESS.matcher(txtLoginup.text.toString()).matches()){
            txtLayoutLoginup.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtPasswordup.text!!.isEmpty()){
            txtLayoutPasswordup.error = getString(R.string.mustNotBeEmpty)
            return false
        }else
        if (txtPasswordRep.text!!.isEmpty()){
            txtLayoutPasswordRep.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        return true
    }
    private fun validate1(): Boolean {


       if  (txtPasswordup.text.toString() != txtPasswordRep.text.toString()){
           Toast.makeText(this@SecondActivity,"please check your password",Toast.LENGTH_SHORT).show()
           return false;
        }
        return true
    }
    }


