package com.apptive.android.imhome.write

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.fragment.findNavController
import com.apptive.android.imhome.R
import com.apptive.android.imhome.baseClass.BaseFragment
import com.apptive.android.imhome.feed.Feed
import com.apptive.android.imhome.feed.FeedInteractor
import java.util.*

class WriteFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_write, container, false)

        val writeButton = rootView.findViewById<Button>(R.id.writeButton)
        val ETcontent = rootView.findViewById<EditText>(R.id.writeContent)
        val image = rootView.findViewById<ImageView>(R.id.writeImageView)
        var categoryCK = false

        val feedInteractor=FeedInteractor()
        feedInteractor.publishCreateSuccess.subscribe {
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
                val feed= Feed("닉네임1", Date(),null,content)
                feedInteractor.createData(feed)

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
                    when(position) {
                        1   ->  {

                        }
                        2   ->  {

                        }
                        3   ->  {

                        }
                    }
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
                var ImageData: Uri? = data?.data
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


}