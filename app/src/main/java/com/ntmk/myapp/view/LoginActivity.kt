package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityHomeBinding
import com.ntmk.myapp.databinding.ActivityLoginBinding
import com.ntmk.myapp.databinding.ActivityMainBinding
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view_model.Login_ViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var list_user : ArrayList<User>
    private lateinit var database : DatabaseReference
    private lateinit var binding: ActivityLoginBinding
    private var load : RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login )

        init()
        var loginViewModel : Login_ViewModel = Login_ViewModel()
        binding.loginn = loginViewModel



        binding.txtLinkSignup.setOnClickListener{
            val i= Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }
        binding.txtForgotPass.setOnClickListener{
            val i= Intent(this, ForgotPassActivity::class.java)
            startActivity(i)
        }
        binding.btnLogin.setOnClickListener{
            val v = View.inflate(this, R.layout.fragment_loading, null)

            val builder = AlertDialog.Builder(this)
            builder.setView(v)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
    }
    fun init(){
        load = findViewById(R.id.loading)
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

