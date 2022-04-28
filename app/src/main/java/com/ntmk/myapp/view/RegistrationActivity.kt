package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityRegistrationBinding
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view_model.Listener
import com.ntmk.myapp.view_model.Login_ViewModel
import com.ntmk.myapp.view_model.RegistrationViewModel
import com.ntmk.myapp.view_model.Util.toast

class RegistrationActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        val viewModel: RegistrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding.registration = viewModel
        viewModel.listener = this

        binding.btnSignup.setOnClickListener {
            var checkIsSuccessSignup = viewModel.onClickSignup()
            if (checkIsSuccessSignup) {
                Handler().postDelayed({
                    k@
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 1000)
            }
        }

        binding.txtLinkLogin.setOnClickListener {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
        }
    }

    override fun onSuccess() {
        toast("SignUp Success")
    }

    override fun onFailure(mess: String) {
        toast(mess)
    }
}