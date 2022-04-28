package com.ntmk.myapp.view_model

import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.ntmk.myapp.BR
import com.ntmk.myapp.controller.UserFirebase
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view.LoginActivity
import com.ntmk.myapp.view.RegistrationActivity


class Login_ViewModel : BaseObservable(){
    private var email: String = ""
    private var pass: String = ""
    private lateinit var list_user: ArrayList<User>
    public var messageLoginEmail :ObservableField<String> = ObservableField<String>()
    public var messageLoginPass :ObservableField<String> = ObservableField<String>()
    private var userFirebase : UserFirebase = UserFirebase()
    init {
        userFirebase.getData()
    }
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
        else if(!checkPass.equals("")) {
            messageLoginPass.set(checkPass)
        }else{
            list_user = userFirebase.getListUser()
            var checkLogin : Boolean = false
            for (user in list_user){
                println(user.toString())
//              if(user.email.equals(getEmail()) && user.pass.equals(getPass())){
//                  checkLogin = true
//                  break
//              }
            }
            if(checkLogin){
                //Do
            }else{
                //Do
            }            }
    }
    fun onClickSignup(){
//        val i : Intent = Intent(activity, RegistrationActivity::class.java)
//        startActivity(i)

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