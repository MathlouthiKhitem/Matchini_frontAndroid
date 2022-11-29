package com.example.matchinii.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.matchinii.R
import com.example.matchinii.models.Message
import com.example.matchinii.models.User
import com.example.matchinii.viewmodels.ApiInterface
import com.example.matchinii.viewmodels.RetrofitClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var post_add: Button
    lateinit var addComment: FloatingActionButton
    var messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessagingAdapter
    lateinit var post_message: EditText
    lateinit var services: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_chat, container, false)
        post_add = rootView.findViewById(R.id.btn_send)
        post_message = rootView.findViewById(R.id.et_message)
        val retrofit = RetrofitClient.getInstance()


        services = retrofit.create(ApiInterface::class.java)
        post_add.setOnClickListener {
            services.AddPost(

                  // email = intent.getStringExtra("login").toString(),
                post_message.text.toString()
            ).enqueue(object :
                Callback<User> {
                //  services.AddPost(1111,post_message.text.toString()).enqueue(object :Callback<User>{
                override fun onResponse(
                    call: Call<User>, response:
                    Response<User>
                ) {
                    val user = response.body()
                    if (user != null) {

                        post_message.text.clear()
                        val intent = Intent(context, HomeActivity::class.java)
                        startActivity(intent)

                    } else {

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    post_message.text.clear()
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
                }

            })
        }
        //   super.onViewCreated(view, savedInstanceState)


        return rootView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}