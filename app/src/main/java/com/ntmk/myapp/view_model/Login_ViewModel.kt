package com.ntmk.myapp.view_model

import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ntmk.myapp.BR
import com.ntmk.myapp.model.User


class Login_ViewModel : BaseObservable(){
    private var email: String = ""
    private var pass: String = ""
    var messageLoginEmail :ObservableField<String> = ObservableField<String>()
    var messageLoginPass :ObservableField<String> = ObservableField<String>()

    @Bindable
    fun getEmail() : String{
        return this.email
    }
    fun setEmail(email:String){
        this.email = email
        notifyPropertyChanged(BR.email)
    }
    @Bindable
    fun getPass() : String{
        return this.pass
    }
    fun setPass(pass:String){
        this.pass = pass
        notifyPropertyChanged(BR.pass)
    }

    fun onClickLogin(){
        var checkEmail : String = checkEmail(getEmail())
        var checkPass : String = checkPass(getPass())
        if(!checkEmail.equals("")) {
            messageLoginEmail.set(checkEmail)
        }
        if(!checkPass.equals("")) {
            messageLoginPass.set(checkPass)
        }
    }
    fun onChangedTextEmail(){
        messageLoginEmail.set("")

    }
    fun onChangedTextPass(){
        messageLoginPass.set("")

    }
    fun checkEmail(email:String) : String {
        var result: String = ""
        if (TextUtils.isEmpty(email)) {
            result = "Email cannot be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            result = "Invalid Email"
        }
        return result
    }
    fun checkPass(pass:String) : String {
        var result : String = ""
        if(TextUtils.isEmpty(pass)){
            result = "Password cannot be empty"
        }else if(pass.length < 8){
            result = "Password length should be 8 characters"
        }
        return result
    }

}