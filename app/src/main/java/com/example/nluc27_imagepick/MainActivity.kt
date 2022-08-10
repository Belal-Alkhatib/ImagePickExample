package com.example.nluc27_imagepick

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.nluc27_imagepick.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(this.layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgPick.setOnClickListener {
          //  val gallery = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
           // startActivityForResult(gallery,100)
             if(Build.VERSION.SDK_INT >= 23){
                 if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                     requestPermissions(arrayOf(Manifest.permission.CAMERA),2000)
                     return@setOnClickListener
                 }else{
                     val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                     startActivityForResult(camera , 200)
                 }
            }else{
                 val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                 startActivityForResult(camera , 200)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Activity.RESULT_OK && requestCode == 100){
            imgPick.setImageURI(data!!.data)
        }else if(requestCode == Activity.RESULT_OK && requestCode == 200){
            val bitmap = data!!.extras!!.get("data")
            imgPick.setImageBitmap(bitmap as Bitmap)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            2000 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(camera , 200)
                }else{
                    finish()
                }
            }
        }
    }
}