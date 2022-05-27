package com.ntmk.myapp

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaos.view.PinView
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseApp.initializeApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.google.firebase.FirebaseApp.initializeApp
import com.ntmk.myapp.adapters.FlowerCartAdapter
import com.ntmk.myapp.adapters.ReceiptAdapter
import com.ntmk.myapp.databinding.ActivityCartBinding
import com.ntmk.myapp.databinding.ActivityReceiptBinding
import com.ntmk.myapp.model.FlowerCart
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view.CartActivity
import com.ntmk.myapp.view.ForgotPassActivity
import com.ntmk.myapp.view.HomeActivity
import com.ntmk.myapp.view.ui.profile.BalanceFragment
import java.util.concurrent.TimeUnit

class ReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiptBinding
    private lateinit var mListFlowerCart: ArrayList<FlowerCart>
    private lateinit var mAdapter: ReceiptAdapter
    private lateinit var mCode: String
    private lateinit var phoneNumber: String
    private lateinit var mToken: PhoneAuthProvider.ForceResendingToken
    lateinit var mDatabase: DatabaseReference

    // if code sending failed , will used to resend
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mVerificationId: String? = null
    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private lateinit var firebaseAuth: FirebaseAuth

    // Progress Dialog
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mListFlowerCart = ArrayList()
        mAdapter = ReceiptAdapter(mListFlowerCart)
        binding.flowerCart.layoutManager = LinearLayoutManager(this)
        binding.flowerCart.setHasFixedSize(true)
        binding.flowerCart.adapter = mAdapter
        getFlowerData()
        init()

        // PrigressDialog
        progressDialog.setTitle("Please wail")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnConfirm.setOnClickListener {
            var userAuth = firebaseAuth.currentUser
            var database = FirebaseDatabase.getInstance().getReference("Users")
            var list_user = ArrayList<User>()
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    for (data in p0.children) {
                        var user = data.getValue(User::class.java)
                        list_user.add(user as User)
                    }
                    for (user in list_user) {
                        if (user.email.equals(userAuth?.email)) {
                            phoneNumber = user.phone.toString()
                        }
                    }
                    if (!phoneNumber.equals("")) {
                        phoneNumber = "+84" + phoneNumber.toInt()
                        println("Phone : " + phoneNumber)

                        // Show dialog
                        val v = View.inflate(this@ReceiptActivity, R.layout.verify_email_fragment, null)
                        val builder = AlertDialog.Builder(this@ReceiptActivity)
                        builder.setView(v)
                        val dialog = builder.create()

                        v.findViewById<View>(R.id.btn_submit).setOnClickListener {
                            var txtNum: PinView = v.findViewById<View>(R.id.id_otp) as PinView
                            var number: String = txtNum.text.toString().trim()
                            sendOtpCode(mVerificationId!!, number)
                            dialog.dismiss()
                        }
                        v.findViewById<View>(R.id.btn_resend).setOnClickListener {
                            if (!phoneNumber.equals("")) {
                                resendPhoneNumberVerification(phoneNumber, forceResendingToken!!)
                            } else {
                                Toast.makeText(this@ReceiptActivity, "Please update user information", Toast.LENGTH_SHORT).show()
                            }
                        }

                        dialog.show()
                        dialog.window?.setLayout(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                        )
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
                        dialog.window?.setGravity(Gravity.BOTTOM)

                        startPhoneNumberVerification(phoneNumber)

                    } else {
                        Toast.makeText(this@ReceiptActivity, "Please update user information", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })


        }
    }

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        getAddressUser()

        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                println("SUCCES")
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
                println("FAILED")
                println(e.message)
                progressDialog.dismiss()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                Toast.makeText(this@ReceiptActivity, "OTP sent, check your message", Toast.LENGTH_SHORT).show()
                mVerificationId = verificationId
                forceResendingToken = token
                progressDialog.dismiss()
            }
        }
    }


    fun startPhoneNumberVerification(phone: String) {
        progressDialog.setTitle("Sending OTP code...")
        progressDialog.show()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBacks!!)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendPhoneNumberVerification(phone: String, token: PhoneAuthProvider.ForceResendingToken) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBacks!!)
            .setForceResendingToken(token)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setTitle("Verifying Code...")
        progressDialog.show()
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
//                var database = FirebaseDatabase.getInstance().getReference("FlowerCart")
//                database.removeValue()
                Toast.makeText(this@ReceiptActivity, "Payment successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                var userAuth = firebaseAuth.currentUser
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "The OTP you entered is not correct", Toast.LENGTH_SHORT).show()
            }
    }

    fun sendOtpCode(verifiID: String, strOTP: String) {
        progressDialog.setTitle("Verifying Code...")
        progressDialog.show()

        val credential = PhoneAuthProvider.getCredential(verifiID, strOTP)
        signInWithPhoneAuthCredential(credential)
    }

    fun getFlowerData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("FlowerCart")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    mListFlowerCart.removeAll(mListFlowerCart)
                    for (data in p0.children) {
                        val flower = data.getValue(FlowerCart::class.java)
                        mListFlowerCart.add(flower!!)
                    }
                    var total_Price: Int = 0
                    for (flower in mListFlowerCart) {
                        total_Price = total_Price + flower.quantity * getNumberPrice(flower.price)
                    }
                    binding.txtTotalPrice.setText("$" + total_Price.toString())
                    binding.flowerCart.adapter = mAdapter
                    binding.flowerCart.adapter?.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }

    fun getNumberPrice(string: String): Int {
        var number = ""
        number = string.replace("\$", "")
        println(number)
        return number.toInt()
    }

    fun getAddressUser() {
        var database = FirebaseDatabase.getInstance().getReference("Users")
        var list_user = ArrayList<User>()
        var userAuth = FirebaseAuth.getInstance().currentUser
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    var user = data.getValue(User::class.java)
                    list_user.add(user as User)
                }
                var emailAuth = userAuth?.email
                for (user in list_user) {
                    if (user.email.equals(emailAuth)) {
                        binding.txtAddress.setText(user.address)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }

}
