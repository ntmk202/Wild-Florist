package com.ntmk.myapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ntmk.myapp.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler().postDelayed({k@
            startActivity(Intent(this@MainActivity, AdvertisementActivity::class.java))
            finish()
        }, 2000)
    }
}