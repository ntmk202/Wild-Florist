package com.ntmk.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RegistrationActivity : AppCompatActivity() {

    private var link_signIn: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        link_signIn = findViewById(R.id.txtLink_login)

        link_signIn?.setOnClickListener{
            val i= Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
        }


    }
}