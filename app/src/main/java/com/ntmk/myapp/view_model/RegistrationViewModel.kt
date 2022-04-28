package com.ntmk.myapp.view_model

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    var name: String = ""
    var email: String = ""
    var pass: String = ""
    var checkBox = false
    var messageLoginName: ObservableField<String> = ObservableField<String>()
    var messageLoginEmail: ObservableField<String> = ObservableField<String>()
    var messageLoginPass: ObservableField<String> = ObservableField<String>()
    var listener: Listener? = null


    fun onClickLogin(): Boolean {
        var check = false
        var checkName: String = checkName(name)
        var checkEmail: String = checkEmail(email)
        var checkPass: String = checkPass(pass)

        if (!checkEmail.equals("") || !checkPass.equals("") || !checkName.equals("")) {
            messageLoginName.set(checkName)
            messageLoginEmail.set(checkEmail)
            messageLoginPass.set(checkPass)

        } else if (!checkBox) {
            messageLoginPass.set("You are a robot ?")
        } else {
            var checkLogin: Boolean = false
//            list_user = userFirebase.getListUser()
//            println("SIZE : "+list_user.size)
//            for (user in list_user){
//                println(user.toString())
//                if(user.email.equals(email) && user.pass.equals(pass)){
//                    checkLogin= true
//                }
//            }
            if (!checkLogin) {
                listener?.onSuccess()
                check = true
            } else {
                listener?.onFailure("Email or password is incorrect")
            }
        }
        return check
    }

    fun onCheckedChanged() {
        if (checkBox) {
            checkBox = false
        } else {
            checkBox = true
        }
        messageLoginPass.set("")

    }

    fun onChangedTextName() {
        messageLoginName.set("")

    }

    fun onChangedTextEmail() {
        messageLoginEmail.set("")

    }

    fun onChangedTextPass() {
        messageLoginPass.set("")

    }

    private fun checkName(name: String): String {
        var result: String = ""
        if (name.length < 3) {
            result = "Name length greater than 3 characters"
        }
        return result
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