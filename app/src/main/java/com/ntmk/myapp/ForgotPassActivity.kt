package com.ntmk.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForgotPassActivity : AppCompatActivity() {

    private var btn_back : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        btn_back = findViewById(R.id.btn_back)

        btn_back?.setOnClickListener{
            val i= Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}