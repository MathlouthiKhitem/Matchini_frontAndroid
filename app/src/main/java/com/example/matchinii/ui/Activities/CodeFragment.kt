 package com.example.matchinii.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.Button
import com.example.matchinii.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CodeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var button : Button? = null
    lateinit var codeEdit: TextInputEditText
    lateinit var codeLayout: TextInputLayout

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
        var view = inflater.inflate(R.layout.fragment_code, container, false)
        val bundle = arguments
        val code =  bundle!!.getString("code")
        val login =  bundle!!.getString("login")
          button = view.findViewById(R.id.button)
        codeEdit =view. findViewById(R.id.ancientPasswordEditText)
        codeLayout =view. findViewById(R.id.ancientPasswordLayout)
        button!!.setOnClickListener(){
            Log.e("intent de fragment " , code.toString())
            val message=codeEdit.text.toString()
            if(code==message){
                startActivity (Intent(requireContext(), ForgotPasswordActivity::class.java).apply {
                    putExtra("login" , login)

                    Log.e("login intent fragment " , login.toString())
                })


            } else {
                codeLayout.error = getString(R.string.mustNotBeEmpty)

            }

            }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CodeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CodeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)

                }
            }
    }
}