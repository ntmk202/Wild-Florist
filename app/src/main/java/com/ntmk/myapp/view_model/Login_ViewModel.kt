package com.ntmk.myapp.view_model

import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ntmk.myapp.controller.UserFirebase
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view.RegistrationActivity


class Login_ViewModel : ViewModel() {
    var email: String = ""
    var pass: String = ""
    var messageLoginEmail: ObservableField<String> = ObservableField<String>()
    var messageLoginPass: ObservableField<String> = ObservableField<String>()
    var listener: Listener? = null
    private var list_user: ArrayList<User> = ArrayList()
    var userFirebase: UserFirebase = UserFirebase()

    init {
        userFirebase.getData()
    }

    fun onClickLogin(): Boolean {
        var checkIsSuccessLogin = false
        var checkEmail: String = checkEmail(email)
        var checkPass: String = checkPass(pass)
        if (!checkEmail.equals("") || !checkPass.equals("")) {
            messageLoginEmail.set(checkEmail)
            messageLoginPass.set(checkPass)
        } else {
            var checkInfoDatabase: Boolean = false
            list_user = userFirebase.getListUser()
            for (user in list_user) {
                if (user.email.equals(email) && user.pass.equals(pass)) {
                    checkInfoDatabase = true
                }
            }
            if (checkInfoDatabase) {
                listener?.onSuccess()
                checkIsSuccessLogin = true
            } else {
                listener?.onFailure("Email or password is incorrect")
            }
        }
        return checkIsSuccessLogin
    }

    fun onChangedTextEmail() {
        messageLoginEmail.set("")

    }

    fun onChangedTextPass() {
        messageLoginPass.set("")

    }

    private fun checkEmail(email: String): String {
        var result: String = ""
        if (TextUtils.isEmpty(email)) {
            result = "Email cannot be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            result = "Invalid Email"
        }
        return result
    }

    private fun checkPass(pass: String): String {
        var result: String = ""
        if (TextUtils.isEmpty(pass)) {
            result = "Password cannot be empty"
        } else if (pass.length < 8) {
            result = "Password length should be 8 characters"
        }
        return result
    }

}