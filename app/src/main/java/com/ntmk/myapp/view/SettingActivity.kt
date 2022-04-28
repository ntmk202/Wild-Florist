package com.ntmk.myapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.ntmk.myapp.R

class SettingActivity : AppCompatActivity() {

    private var back: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        back = findViewById(R.id.link_back)

        back?.setOnClickListener{

        }
    }
}