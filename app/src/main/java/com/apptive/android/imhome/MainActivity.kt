package com.apptive.android.imhome

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, ImageData)
                    val pt: ImageView = findViewById(R.id.writeImageView)
                    val resize = resizeBitmap(bitmap, 300, 300)
                    pt.setImageBitmap(resize)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, width:Int, height:Int): Bitmap {
        return Bitmap.createScaledBitmap(
            bitmap,
            300,
            300,
            false
        )
    }
}