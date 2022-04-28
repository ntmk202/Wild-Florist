package com.ntmk.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ForgotPassActivity : AppCompatActivity() {

    private var link_signUp : TextView? = null
    private var btn_back : Button? = null
    private var btn_confirm : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        link_signUp = findViewById(R.id.txtLink_signup)
        btn_back = findViewById(R.id.btn_back)
        btn_confirm = findViewById(R.id.btn_confirm)

//        link_signUp?.setOnClickListener{
//            val i= Intent(this, RegistrationActivity::class.java)
//            startActivity(i)
//        }
//        btn_back?.setOnClickListener{
//            val i= Intent(this, LoginActivity::class.java)
//            startActivity(i)
//        }

        btn_confirm?.setOnClickListener{
            val v = View.inflate(this, R.layout.verify_email_fragment, null)

            val builder = AlertDialog.Builder(this)
            builder.setView(v)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}