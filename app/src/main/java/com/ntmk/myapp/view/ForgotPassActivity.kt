package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_pass)

        binding.txtLinkSignup.setOnClickListener{
            val i= Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }
        binding.btnBack.setOnClickListener{
            val i= Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnConfirm.setOnClickListener{
            val v = View.inflate(this, R.layout.verify_email_fragment, null)

            val builder = AlertDialog.Builder(this)
            builder.setView(v)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}