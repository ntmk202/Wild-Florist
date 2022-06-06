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
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference
    var userFirebase: UserFirebase = UserFirebase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        init()

        binding.btnSignup.setOnClickListener {
            register()
        }

        binding.txtLinkLogin.setOnClickListener {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
        }

        onChangedText()
    }
    private fun init(){
        progressDialog  = ProgressDialog(this)
        userFirebase.getData()
        database = FirebaseDatabase.getInstance().getReference("Users")
        auth =  FirebaseAuth.getInstance()
    }

    private fun register(){
        var name : String = binding.txtNameRegis.text.toString()
        var email : String = binding.txtEmailRegis.text.toString()
        var pass : String = binding.txtPassRegis.text.toString()

        var checkName: String = checkName(name)
        var checkEmail: String = checkEmail(email)
        var checkPass: String = checkPass(pass)

        if (!checkEmail.equals("") || !checkPass.equals("") || !checkName.equals("")) {
            binding.txtMessageName.text = checkName
            binding.txtMessageEmail.text = checkEmail
            binding.txtMessagePass.text = checkPass

        } else if (!binding.checkboxRobot.isChecked) {
            binding.txtMessagePass.setText("Are you a robot?")
        } else {

            list_user = userFirebase.getListUser()
            var list_name: ArrayList<String> = ArrayList()
            var list_email: ArrayList<String> = ArrayList()
            for (user in list_user) {
                list_name.add(user.name!!)
                list_email.add(user.email!!)
            }

            when {

                name in list_name -> {
                    Toast.makeText(this,"Name already exists", Toast.LENGTH_SHORT).show()
                }
                email in list_email -> {
                    Toast.makeText(this,"Email already exists", Toast.LENGTH_SHORT).show()
                }
                else -> {
        //                val userId = cou
        //                var userId : Int = 0
        //                if(list_user.size == 0){
        //                    userId = randomId
        //                }else{
        //                    userId = list_user.get(list_user.size - 1).userId + randomId
        //                }
                    progressDialog.setTitle("Registering ...")
                    progressDialog.show()

                    sendDataAuth(name, email, pass)

                }
            }
        }
    }

    private fun sendDataAuth(name : String , email:String , pass : String){
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                var currentUser = FirebaseAuth.getInstance().currentUser!!
                currentUser.sendEmailVerification()
                progressDialog.dismiss()

                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }

                currentUser!!.updateProfile(profileUpdates)

                // add data
                val user = User(name, email, pass,"","")
//                val currentUSerDb = database?.child((currentUser?.uid!!))


                database.child((currentUser?.uid!!)).setValue(user)
//                currentUSerDb?.child("name")?.setValue(binding.txtNameRegis.text.toString())
//                currentUSerDb?.child("email")?.setValue(binding.txtEmailRegis.text.toString())
//                currentUSerDb?.child("pass")?.setValue(binding.txtPassRegis.text.toString())
//                currentUSerDb?.child("phone")?.setValue(null)
//                currentUSerDb?.child("address")?.setValue(null)

                progressDialog.dismiss()

                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)

                Toast.makeText(this,"Registration Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onChangedText(){
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
                binding.txtMessageName.text = checkName
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
                binding.txtMessageEmail.text = checkEmail
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
                binding.txtMessagePass.text = checkPass
            }
        })

        binding.checkboxRobot.setOnClickListener {
            if(binding.checkboxRobot.isChecked){
                binding.txtMessagePass.text = ""
            }
        }
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