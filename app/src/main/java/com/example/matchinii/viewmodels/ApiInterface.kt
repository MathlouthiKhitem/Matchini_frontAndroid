package com.example.matchinii.viewmodels
import com.example.matchinii.models.User
import com.example.matchinii.models.bool
import com.example.matchinii.models.data
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
//
//    @POST("login")
//    fun seConnecter(@Body map: HashMap<String ,String>):Call<User>

   @POST("/user/login")
  fun login(@Body map: HashMap<String ,String>):Call<User>
   // @POST("/user/signup")
  //  fun register(@Body map: HashMap<String ,String>):Call<User>
   @Multipart
   @POST("/user/signup")
   //fun register(@Body map: HashMap<String ,String>):Call<User>

   fun register(@Part login : MultipartBody.Part,@Part password : MultipartBody.Part ,@Part Age: MultipartBody.Part,@Part Image:MultipartBody.Part ,@Part("Image") name : RequestBody) :Call<User>
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
    @POST("/user/getUser")
    fun getUser(@Body map: HashMap<String, String>):Call<ArrayList<User>>
    @POST("/user/getConnectedUser")
    fun getConnectedUser(@Body map: HashMap<String, String>):Call<ArrayList<User>>
    @POST("/user/getObjectId")
    fun getObjectId(@Body map: HashMap<String, String>):Call<data>
    @POST("/user/IsMatched/{login}")
    fun IsMatched(@Path("login") login:String , @Body map: HashMap<String, String>):Call<bool>
    @PUT("/user/addMatches2/{login}")
    fun addMatches2(@Path("login") login:String , @Body map: HashMap<String, String>):Call<User>
    @PUT("/user/addMatches")
    fun addMatches(@Body map: HashMap<String, String>):Call<User>
    @PUT("/user/chatconecte")
    fun chatconecte(@Body map: HashMap<String, String>):Call<User>
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

