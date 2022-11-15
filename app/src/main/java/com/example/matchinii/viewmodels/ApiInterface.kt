package com.example.matchinii.viewmodels
import com.example.matchinii.models.User
import com.example.matchinii.models.data
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiInterface {
//
//    @POST("login")
//    fun seConnecter(@Body map: HashMap<String ,String>):Call<User>
   @POST("/user/login")
  fun login(@Body map: HashMap<String ,String>):Call<User>
    @POST("/user/signup")
    fun register(@Body map: HashMap<String ,String>):Call<User>
    @POST("/user/patchOnce")
    fun updatePassword (@Body map: HashMap<String ,String>):Call<User>
    @POST("/user/googleVerifier")
    fun googleVerifier(@Body  map: HashMap<String ,String>):Call<User>
    @POST("/user/googleSignIn")
    fun googleSignIn(@Body map: HashMap<String ,String>):Call<User>
    @POST("/user/googleSignup")
    fun googleSignUp(@Body map: HashMap<String ,String>):Call<User>
    @PUT("/user/modifier")
    fun modifier(@Body map: HashMap<String ,String>):Call<User>
    @POST("/user/forgot")
    fun forgotPassword(@Body map: HashMap<String, String>):Call<data>
    @PUT("/user/restorPassword")
    fun restorPassword(@Body map: HashMap<String, String>):Call<User>
    companion object {

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:9090")
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
        }
    }
