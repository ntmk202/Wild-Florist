package com.ntmk.myapp.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.controller.UserFirebase
import com.ntmk.myapp.databinding.ActivityRegistrationBinding
import com.ntmk.myapp.model.User


class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var progressDialog : ProgressDialog
    private var list_user: ArrayList<User> = ArrayList()
    var userFirebase: UserFirebase = UserFirebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        binding.btnSignup.setOnClickListener {
            onClickRegister()
        }

        binding.txtLinkLogin.setOnClickListener {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
        }
        onChangedText()
    }
    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        progressDialog  = ProgressDialog(this)
        userFirebase.getData()
    }

    fun onClickRegister(){
        var name : String = binding.txtNameRegis.text.toString()
        var email : String = binding.txtEmailRegis.text.toString()
        var pass : String = binding.txtPassRegis.text.toString()
        var checkName: String = checkName(name)
        var checkEmail: String = checkEmail(email)
        var checkPass: String = checkPass(pass)
        if (!checkEmail.equals("") || !checkPass.equals("") || !checkName.equals("")) {
            binding.txtMessageName.setText(checkName)
            binding.txtMessageEmail.setText(checkEmail)
            binding.txtMessagePass.setText(checkPass)
        } else if (!binding.checkboxRobot.isChecked) {
            binding.txtMessagePass.setText("You are a robot ?")
        } else {
            list_user = userFirebase.getListUser()
            var list_name: ArrayList<String> = ArrayList()
            var list_email: ArrayList<String> = ArrayList()
            for (user in list_user) {
                list_name.add(user.name)
                list_email.add(user.email)
            }
            if (name in list_name) {
                Toast.makeText(this,"Name already exists", Toast.LENGTH_SHORT).show()
            } else if (email in list_email) {
                Toast.makeText(this,"Email already exists", Toast.LENGTH_SHORT).show()
            } else {
                var id : Int = 0
                if(list_user.size == 0){
                    id = 0
                }else{
                    id = list_user.get(list_user.size - 1).id + 1
                }
                var user = User(id, name, email,pass)
                userFirebase.addUser(user)
                sendDataAuth(name,email, pass)

            }
        }
    }
    fun sendDataAuth(name : String , email:String , pass : String){
        var auth = FirebaseAuth.getInstance()
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                progressDialog.dismiss()
                val user = auth.currentUser

                val profileUpdates = userProfileChangeRequest {
                    displayName = name

                }
                user!!.updateProfile(profileUpdates)
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                Toast.makeText(this,"SignUp Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"SignUp Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun onChangedText(){
        binding.txtNameRegis.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {}
            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {}

            override fun afterTextChanged(editable: Editable) {
                var name : String = binding.txtNameRegis.text.toString()
                var checkName: String = checkName(name)
                binding.txtMessageName.setText(checkName)
            }
        })
        binding.txtEmailRegis.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {}
            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {}

            override fun afterTextChanged(editable: Editable) {
                var Email : String = binding.txtEmailRegis.text.toString()
                var checkEmail: String = checkEmail(Email)
                binding.txtMessageEmail.setText(checkEmail)
            }
        })
        binding.txtPassRegis.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {}
            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {}

            override fun afterTextChanged(editable: Editable) {
                var Pass : String = binding.txtPassRegis.text.toString()
                var checkPass: String = checkPass(Pass)
                binding.txtMessagePass.setText(checkPass)
            }
        })
        binding.checkboxRobot.setOnClickListener {
            if(binding.checkboxRobot.isChecked){
                binding.txtMessagePass.setText("")
            }
        }
    }

    fun checkName(name: String): String {
        var result: String = ""
        if (name.length < 3) {
            result = "Name length greater than 3 characters"
        }
        return result
    }

    fun checkEmail(email: String): String {
        var result: String = ""
        if (TextUtils.isEmpty(email)) {
            result = "Email cannot be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            result = "Invalid Email"
        }
        return result
    }

    fun checkPass(pass: String): String {
        var result: String = ""
        if (TextUtils.isEmpty(pass)) {
            result = "Password cannot be empty"
        } else if (pass.length < 8) {
            result = "Password length should be 8 characters"
        }
        return result
    }
}