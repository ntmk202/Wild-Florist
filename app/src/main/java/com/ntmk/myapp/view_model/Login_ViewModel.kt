package com.ntmk.myapp.view_model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.ntmk.myapp.BR
import com.ntmk.myapp.model.User

class Login_ViewModel : BaseObservable() {
    private var email: String = ""
    private var pass: String = ""
    public var messageLogin :ObservableField<String> = ObservableField<String>()

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
        var user : User = User(1,"",getEmail(),getPass())
        if(user.checkName() && user.checkEmail() && user.checkPass()) {
            messageLogin.set("Login success")
        }else{
            messageLogin.set("Check email and password are correct")
        }
    }


}