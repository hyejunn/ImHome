package com.apptive.android.imhome.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment

class LoginFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        val id = rootView.findViewById<EditText>(R.id.idLogin)
        val password = rootView.findViewById<EditText>(R.id.passwordLogin)
        val buttonLogin = rootView.findViewById<Button>(R.id.buttonLoginLogin)
        val buttonSignup = rootView.findViewById<Button>(R.id.buttonSignupLogin)

        buttonLogin.setOnClickListener {


        }

        buttonSignup.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_signupFragment)

        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}