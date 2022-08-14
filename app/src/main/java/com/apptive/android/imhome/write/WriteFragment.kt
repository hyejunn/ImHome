package com.apptive.android.imhome.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.apptive.android.imhome.feed.Feed
import com.apptive.android.imhome.feed.FeedInteractor
import com.apptive.android.imhome.utility.DateUtility
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import com.apptive.android.imhome.utility.UserInfoManager
import java.util.*

class WriteFragment: BaseFragment() {
    private lateinit var rootView : View
    private lateinit var fbStorage : FirebaseStorage
    var ImageData : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_write, container, false)
        lateinit var category:String
        val writeButton = rootView.findViewById<TextView>(R.id.writeButton)
        val ETcontent = rootView.findViewById<EditText>(R.id.writeContent)
        val image = rootView.findViewById<ImageView>(R.id.writeImageView)
        var categoryCK = false

        fbStorage = FirebaseStorage.getInstance()


        val feedInteractor=FeedInteractor()
        feedInteractor.publishCreateSuccess.subscribe {
            funImageUpload(rootView, it)
            Toast.makeText(activity, "작성 완료!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }


        writeButton.setOnClickListener {
            val content = ETcontent.text.toString()
            if (content == "") {
                Toast.makeText(activity, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (!categoryCK) {
                Toast.makeText(activity, "분류를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                // db에 저장

                val id = DateUtility.formatDate(Date(),"yyyyMMddHHmmss")
                val feed= Feed(id,UserInfoManager.getNickname().toString(), Date(),id,content,category)
                feedInteractor.createDataWithId(id, feed)

            }
        }

        image.setOnClickListener {

            startDefaultGalleryApp()
        }

        val items = resources.getStringArray(R.array.categories)

        val spinner = rootView.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                if (position == 0) {
                    categoryCK = false
                }
                else {
                    categoryCK = true
                    category=items[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun startDefaultGalleryApp() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when(requestCode) {
            10 -> {
                ImageData = data?.data
                try {
                    val pt: ImageView = requireView().findViewById(R.id.writeImageView)
                    pt.setImageURI(ImageData)
                    pt.setBackgroundResource(R.color.white)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    private fun funImageUpload(view : View, feed : Feed){
        val id= feed.id
        var imgFileName = id + ".jpeg"
        var storageRef = fbStorage?.reference?.child(imgFileName)
        Log.d("please", "plz")
        storageRef?.putFile(ImageData!!)?.addOnSuccessListener {
            Log.d("please", "success")

        }
    }


}