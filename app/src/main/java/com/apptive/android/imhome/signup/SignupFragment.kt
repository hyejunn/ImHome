package com.apptive.android.imhome.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class SignupFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val nicknameInteractor=NicknameInteractor()
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)
       val toolbar=rootView.findViewById<Toolbar>(R.id.signupToolbar)
        val buttonJoin = rootView.findViewById<Button>(R.id.buttonJoinSignup)
        val ETnickname = rootView.findViewById<EditText>(R.id.nicknameSignup)
        val ETpassword = rootView.findViewById<EditText>(R.id.passwordSignup)
        val ETpwck = rootView.findViewById<EditText>(R.id.pwckSignup)
        val ETemail=rootView.findViewById<EditText>(R.id.emailSignup)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        buttonJoin.setOnClickListener {
            val nickname = ETnickname.text.toString()

            val password = ETpassword.text.toString()
            val pwck = ETpwck.text.toString()
            val email=ETemail.text.toString()
            if (nickname == "" || password == "" || pwck == ""||email=="") {
                Toast.makeText(activity, "입력을 완료해주세요.", Toast.LENGTH_SHORT).show()
            }

            else if (password != pwck) {
                Toast.makeText(activity, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                // db에 저장
                val auth = FirebaseAuth.getInstance()
                //파이어베이스에 신규계정 등록하기
                auth.createUserWithEmailAndPassword(
                    email,
                    password
                )
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(requireContext(),"회원가입 완료",Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                            it.result.user?.uid
                            if(it.result.user?.uid!=null) {
                                nicknameInteractor.createDataWithId(it.result.user?.uid.toString(),Username(nickname))
                            }
                        }else{
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),"회원가입 실패",Toast.LENGTH_SHORT).show()
                        Log.d("회원가입", it.toString())
                    }


            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}