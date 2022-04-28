package com.ntmk.myapp.view_model

import android.text.TextUtils
import android.util.Patterns
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ntmk.myapp.controller.UserFirebase
import com.ntmk.myapp.model.User

class RegistrationViewModel : ViewModel() {
    var name: String = ""
    var email: String = ""
    var pass: String = ""
    var checkBox = false
    var messageLoginName: ObservableField<String> = ObservableField<String>()
    var messageLoginEmail: ObservableField<String> = ObservableField<String>()
    var messageLoginPass: ObservableField<String> = ObservableField<String>()
    var listener: Listener? = null
    private var list_user: ArrayList<User> = ArrayList()
    var userFirebase: UserFirebase = UserFirebase()

    init {
        userFirebase.getData()
    }

    fun onClickSignup(): Boolean {
        var checkIsSuccessSignup = false
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
            list_user = userFirebase.getListUser()
            var list_name: ArrayList<String> = ArrayList()
            var list_email: ArrayList<String> = ArrayList()
            for (user in list_user) {
                list_name.add(user.name)
                list_email.add(user.email)
            }
            if (name in list_name) {
                listener?.onFailure("Name already exists")
            } else if (email in list_email) {
                listener?.onFailure("Email already exists")
            } else {
                var id: Int = list_user.get(list_user.size - 1).id + 1
                var user = User(id, name, email, pass)
                userFirebase.addUser(user)
                listener?.onSuccess()
                checkIsSuccessSignup = true
            }
        }
        return checkIsSuccessSignup
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