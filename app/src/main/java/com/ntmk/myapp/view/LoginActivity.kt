package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
    private lateinit var list_user : ArrayList<User>
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        init()

        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login )
        var loginViewModel : Login_ViewModel = Login_ViewModel()

        













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
    fun init(){
        link_signUp = findViewById(R.id.txtLink_signup)
        link_forgotPass = findViewById(R.id.txtForgot_pass)
        link_start = findViewById(R.id.btnLogin)
        btn_login = findViewById(R.id.btnLogin)
        txt_email = findViewById(R.id.txtEmail)
        txt_pass = findViewById(R.id.txtPass)
        list_user = ArrayList<User>()
    }
//    private fun login() {
//        var check : Boolean = false
//        for (data in list_user){
//            if(txt_email?.text.toString().equals(data.getEmail()) && txt_pass?.text.toString().equals(data.getPass())){
//                Toast.makeText(this,"Login success", Toast.LENGTH_LONG).show()
//                check = true
//            }
//        }
//        if (!check){
//            Toast.makeText(this,"Login failed", Toast.LENGTH_LONG).show()
//        }
//    }
}

