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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityLoginBinding
import com.ntmk.myapp.model.User


open class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressDialog : ProgressDialog
    private var list_user: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.txtLinkSignup.setOnClickListener {
            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }

        binding.txtForgotPass.setOnClickListener {
            val i = Intent(this, ForgotPassActivity::class.java)
            startActivity(i)
        }

        onChangedText()

        // Login google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("172693806312-iaa2eqpmu12ng69b0soi1e36anngej6a.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnLoginGoogle.setOnClickListener {
            val intent = mGoogleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    private fun init(){
        progressDialog  = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun sendDataAuth(email:String , pass : String){
        if(mAuth == null){
            println("NULL")
        }

        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                println()
                if (task.isSuccessful) {
                    progressDialog.dismiss()

                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    Toast.makeText(baseContext, "Login success!", Toast.LENGTH_SHORT).show()

                } else {
                    progressDialog.dismiss()
                    Toast.makeText(baseContext, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun login(){

        var email : String = binding.txtEmail.text.toString()
        var pass : String = binding.txtPass.text.toString()
        var checkEmail: String = checkEmail(email)
        var checkPass: String = checkPass(pass)

        if (!checkEmail.equals("") || !checkPass.equals("")) {
            binding.txtMessageEmail.text = checkEmail
            binding.txtMessagePass.text = checkPass
        } else {
            progressDialog.setTitle("Logged in ...")
            progressDialog.show()
            sendDataAuth(email, pass)
        }
    }

    private fun onChangedText() {
        binding.txtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                var Pass: String = binding.txtPass.text.toString()
                var checkPass: String = checkPass(Pass)
                binding.txtMessagePass.setText(checkPass)
            }
        })

        binding.txtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int, i1: Int, i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                var Email: String = binding.txtEmail.text.toString()
                var checkEmail: String = checkEmail(Email)
                binding.txtMessageEmail.setText(checkEmail)
            }
        })

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

    private companion object {
        private const val RC_SIGN_IN = 1000
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

}

