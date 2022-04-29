package com.ntmk.myapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityLoginBinding
import com.ntmk.myapp.view_model.Listener
import com.ntmk.myapp.view_model.Login_ViewModel
import com.ntmk.myapp.view_model.Util.toast


open class LoginActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel: Login_ViewModel = ViewModelProvider(this)[Login_ViewModel::class.java]
        binding.loginn = viewModel
        viewModel.listener = this


        binding.btnLogin.setOnClickListener {
//            val checkIsSuccessLogin = viewModel.onClickLogin()
//            if (checkIsSuccessLogin) {
                val i = Intent(this, LoadingActivity::class.java)
                startActivity(i)
//            }
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

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(mess: String) {
        toast(mess)
    }
}

