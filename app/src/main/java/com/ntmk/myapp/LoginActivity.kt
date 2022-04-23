package com.ntmk.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private var link_signUp : TextView? = null
    private var link_forgotPass : TextView? = null
    private var link_start : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        link_signUp = findViewById(R.id.txtLink_signup)
        link_forgotPass = findViewById(R.id.txtForgot_pass)
        link_start = findViewById(R.id.btn_login)

        link_signUp?.setOnClickListener{
            val i= Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }
        link_forgotPass?.setOnClickListener{
            val i= Intent(this, ForgotPassActivity::class.java)
            startActivity(i)
        }
        link_start?.setOnClickListener{
            val i= Intent(this, LoadingActivity::class.java)
            startActivity(i)
        }
    }
}

