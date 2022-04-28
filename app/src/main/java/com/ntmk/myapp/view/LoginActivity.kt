package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import com.ntmk.myapp.ForgotPassActivity
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityLoginBinding
import com.ntmk.myapp.databinding.ActivityMainBinding
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view_model.Login_ViewModel

class LoginActivity : AppCompatActivity() {

    private var link_signUp : TextView? = null
    private var link_forgotPass : TextView? = null
    private var link_start : Button? = null
    private var btn_login : Button? = null
    private var txt_email : TextView? = null
    private var txt_pass : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        link_signUp?.setText("222222")

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login )
        var loginViewModel : Login_ViewModel = Login_ViewModel()
        binding.loginn = loginViewModel















        link_forgotPass?.setOnClickListener{
            val i= Intent(this, ForgotPassActivity::class.java)
            startActivity(i)
        }
        link_start?.setOnClickListener{
            val i= Intent(this, LoadingActivity::class.java)
            startActivity(i)
        }
    }
    fun init(){
        link_signUp = findViewById(R.id.txtLink_signup)
        link_forgotPass = findViewById(R.id.txtForgot_pass)
        link_start = findViewById(R.id.btnLogin)
        btn_login = findViewById(R.id.btnLogin)
        txt_email = findViewById(R.id.txtEmail)
        txt_pass = findViewById(R.id.txtPass)
    }

}

