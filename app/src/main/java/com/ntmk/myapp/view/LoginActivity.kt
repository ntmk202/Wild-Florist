package com.ntmk.myapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityLoginBinding
import com.ntmk.myapp.view_model.Listener
import com.ntmk.myapp.view_model.Login_ViewModel
import com.ntmk.myapp.view_model.Util.toast


open class LoginActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private companion object {
        private const val RC_SIGN_IN = 1000
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel: Login_ViewModel = ViewModelProvider(this)[Login_ViewModel::class.java]
        binding.loginn = viewModel
        viewModel.listener = this


        binding.btnLogin.setOnClickListener {
            val checkIsSuccessLogin = viewModel.onClickLogin()
            if (checkIsSuccessLogin) {
                val i = Intent(this, LoadingActivity::class.java)
                startActivity(i)
            }
        }
        binding.txtLinkSignup.setOnClickListener {
            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
        }
        binding.txtForgotPass.setOnClickListener {
            val i = Intent(this, ForgotPassActivity::class.java)
            startActivity(i)
        }

        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("172693806312-iaa2eqpmu12ng69b0soi1e36anngej6a.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.btnLoginGoogle.setOnClickListener {
            val intent = mGoogleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
//            try {
//                val accountTast = GoogleSignIn.getSignedInAccountFromIntent(data)
//                val account = accountTast.getResult(ApiException::class.java)
//                firebaseAuthWithGoogleAccount()
//            } catch (e: Exception) {
//                Toast.makeText(this, "Loggin Failed", Toast.LENGTH_SHORT).show()
//            }
        }

    }

//    private fun firebaseAuthWithGoogleAccount() {
//        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
//        mAuth.signInWithCredential(credential)
//            .addOnSuccessListener { authResult ->
//                //get loggedIn user
//                val firebaseUser = mAuth.currentUser
//                //get user info
//                val uid = firebaseUser?.uid
//                val email = firebaseUser?.email
//
//                if(authResult.additionalUserInfo!!.isNewUser){
//                    Toast.makeText(this,"Account created...\n$email",Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(this,"LoggedIn...\n$email",Toast.LENGTH_SHORT).show()
//                }
//
//            }.addOnFailureListener { e ->
//                Toast.makeText(this,"Loggin Failed",Toast.LENGTH_SHORT).show()
//            }
//        finish()
//        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
//    }

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(mess: String) {
        toast(mess)
    }
}

