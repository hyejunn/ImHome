package com.apptive.android.imhome.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class LoginFragment:BaseFragment() {
    val auth:FirebaseAuth= Firebase.auth
    val currentUser=auth.currentUser



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

            login(requireActivity(),id.text.toString(),password.text.toString())

        }

        if(currentUser!=null){
            //이거 나중에 런치 화면에서 체크해서 넘기기로
            Log.d("checfor","이미 로그인된 사용자 : ${currentUser.uid}")
        //    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToFeedFragment(currentUser.uid))

        }

        buttonSignup.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_signupFragment)

        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun login(activity: Activity, email:String, password:String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    Log.d("checkfor", "signInWithEmail:success  | user : ${user?.uid}")
                  //  findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToFeedFragment(currentUser?.uid.toString()))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("checkfor", "signInWithEmail:failure", task.exception)
                   Toast.makeText(requireContext(),"이메일과 비밀번호를 다시 확인해주세요.",Toast.LENGTH_SHORT).show()
                }
            }
    }
}