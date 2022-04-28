package com.ntmk.myapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ntmk.myapp.R
import com.ntmk.myapp.controller.UserFirebase
import com.ntmk.myapp.databinding.ActivityLoginBinding
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view_model.Listener
import com.ntmk.myapp.view_model.Login_ViewModel
import com.ntmk.myapp.view_model.Util.toast


open class LoginActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityLoginBinding
    private var load: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel: Login_ViewModel = ViewModelProvider(this).get(Login_ViewModel::class.java)
        binding.loginn = viewModel
        viewModel.listener = this


        binding.btnLogin.setOnClickListener {
            var checkIsSuccessLogin = viewModel.onClickLogin()
            if (checkIsSuccessLogin) {
                val v = View.inflate(this, R.layout.fragment_loading, null)

                val builder = AlertDialog.Builder(this)
                builder.setView(v)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        binding.txtLinkSignup.setOnClickListener {
            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }
        binding.txtForgotPass.setOnClickListener {
            val i = Intent(this, ForgotPassActivity::class.java)
            startActivity(i)
        }
    }

    fun init() {
        load = findViewById(R.id.loading)
    }

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(mess: String) {
        toast(mess)
    }
}

