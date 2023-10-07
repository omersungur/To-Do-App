package com.omersungur.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omersungur.todoapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*binding.textView.setOnClickListener {
            binding.textView.textSize = 20F
            binding.cv.setCardBackgroundColor(0x55FFFF00)
        }

        binding.textView2.setOnClickListener {
            //binding.cv.setBackgroundColor(0xFF00FF00.toInt())
            binding.cv.setCardBackgroundColor(0xFF00FF00.toInt())
        }*/
    }
}