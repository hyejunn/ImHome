package com.apptive.android.imhome.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment

class SignupFragment:BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)
        val buttonBack = rootView.findViewById<Button>(R.id.buttonbackSignup)
        val buttonJoin = rootView.findViewById<Button>(R.id.buttonJoinSignup)
        val ETnickname = rootView.findViewById<EditText>(R.id.nicknameSignup)
        val ETid = rootView.findViewById<EditText>(R.id.idSignup)
        val ETpassword = rootView.findViewById<EditText>(R.id.passwordSignup)
        val ETpwck = rootView.findViewById<EditText>(R.id.pwckSignup)
        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        buttonJoin.setOnClickListener {
            val nickname = ETnickname.text.toString()
            val id = ETid.text.toString()
            val password = ETpassword.text.toString()
            val pwck = ETpwck.text.toString()
            if (nickname == "" || id == "" || password == "" || pwck == "") {
                Toast.makeText(activity, "입력을 완료해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (password != pwck) {
                Toast.makeText(activity, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                // db에 저장
                findNavController().popBackStack()
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}