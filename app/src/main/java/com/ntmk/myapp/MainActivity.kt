package com.ntmk.myapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ntmk.myapp.view.AdvertisementActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(applicationContext,"Its a toast!", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({k@
            startActivity(Intent(this@MainActivity, AdvertisementActivity::class.java))
            finish()
        }, 2000)
    }
}