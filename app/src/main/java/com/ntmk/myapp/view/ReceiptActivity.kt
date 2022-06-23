package com.ntmk.myapp.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaos.view.PinView
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.adapters.ReceiptAdapter
import com.ntmk.myapp.databinding.ActivityReceiptBinding
import com.ntmk.myapp.model.FlowerCart
import com.ntmk.myapp.model.Order
import com.ntmk.myapp.model.User
import java.text.SimpleDateFormat
import java.util.*


class ReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiptBinding
    private lateinit var mListFlowerCart: ArrayList<FlowerCart>
    private lateinit var mAdapter: ReceiptAdapter

    private lateinit var userAuth : FirebaseUser
    private lateinit var phoneNumber: String

////    if code sending failed , will used to resend
//    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
//    private var mVerificationId: String? = null
//    private var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
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

        // ProgressDialog
        progressDialog.setTitle("Please wail")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnConfirm.setOnClickListener {

            var userAuth = firebaseAuth.currentUser
            var currentUser = FirebaseAuth.getInstance().currentUser!!
            phoneNumber = userAuth?.phoneNumber.toString()
            var database = FirebaseDatabase.getInstance().getReference("Users")
            var list_user = ArrayList<User>()

            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    for (data in p0.children) {
                        var user = data.getValue(User::class.java) as User
                        list_user.add(user as User)
                    }
//                    for (user in list_user) {
//                        if (user.email.equals(userAuth?.email)) {
//                            phoneNumber = user.phone.toString()
//                        }
//                    }

//                    if (!phoneNumber.equals("")) {
//                        phoneNumber = "+84" + phoneNumber.toInt()
//                        println("Phone : " + phoneNumber)

                        // Show dialog
                        val v =
                            View.inflate(this@ReceiptActivity, R.layout.verify_email_fragment, null)
                        val builder = AlertDialog.Builder(this@ReceiptActivity)
                        builder.setView(v)
                        val dialog = builder.create()
                        var id_otp = v.findViewById<PinView>(R.id.id_otp);
                        v.findViewById<View>(R.id.btn_submit).setOnClickListener {
//                            var txtNum: PinView = v.findViewById<View>(R.id.id_otp) as PinView
//                            var number: String = txtNum.text.toString().trim()
//                            sendOtpCode(mVerificationId!!, number)
//                            dialog.dismiss()

                            progressDialog.setTitle("Paying...")
                            progressDialog.show()
                            if (id_otp.text.toString().equals("123456")) {
                                Toast.makeText(
                                    this@ReceiptActivity,
                                    "Payment successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                intent()
                                var userId = userAuth?.uid!!
                                var mDatabaseCart = FirebaseDatabase.getInstance().getReference("FlowerCart").child(userId)
                                mDatabaseCart.removeValue()

                                var mDatabaseOrder = FirebaseDatabase.getInstance().getReference("FlowerOrder").child(userId)
                                var order  = Order();
                                order.listFlower = mListFlowerCart
                                order.status = "To Pay"
                                val sdf1 = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
                                val sdf2 = SimpleDateFormat("ddMMyyyy")
                                val currentDate = sdf1.format(Date())
                                order.timeOrder = currentDate

                                order.id = sdf2.format(Date()) + randomAlphaNumeric();
                                order.orderTotal = binding.txtTotalPrice.text.toString()

                                mDatabaseOrder.child(order.id.toString()).setValue(order)

                            } else {
                                Toast.makeText(
                                    this@ReceiptActivity,
                                    "OTP is incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        v.findViewById<View>(R.id.btn_resend).setOnClickListener {
                            if (!phoneNumber.equals("")) {
//                                resendPhoneNumberVerification(phoneNumber, forceResendingToken!!)
                            } else {
                                Toast.makeText(
                                    this@ReceiptActivity,
                                    "Please update user information",
                                    Toast.LENGTH_SHORT
                                ).show()
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

//                        startPhoneNumberVerification(phoneNumber)

//                    } else {
//                        Toast.makeText(
//                            this@ReceiptActivity,
//                            "Please update user information",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })


        }
    }

    private val alphaUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val digits = "0123456789" // 0-9
    private val ALPHA_NUMERIC: String = alphaUpperCase + digits
    private fun randomAlphaNumeric(): String? {
        val sb = StringBuilder()
        for (i in 0 until 8) {
            if(i < 4){
                val number: Int = Random().nextInt(alphaUpperCase.length)

                val ch: Char = alphaUpperCase.get(number)
                sb.append(ch)
            }else if(i == 4 ){
                val number: Int = Random().nextInt(digits.length)
                val ch: Char = digits.get(number)
                sb.append(ch)
            }else{
                val number: Int = Random().nextInt(ALPHA_NUMERIC.length)
                val ch: Char = ALPHA_NUMERIC.get(number)
                sb.append(ch)
            }
        }
        return sb.toString()
    }


    private fun intent() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        userAuth = FirebaseAuth.getInstance().currentUser!!
        getAddressUser()
    }

    fun getFlowerData() {
        var userId = FirebaseAuth.getInstance().currentUser?.uid!!
        var mDatabase = FirebaseDatabase.getInstance().getReference("FlowerCart").child(userId)
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



//        init()

//        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                println("SUCCESS")
//                Log.d(TAG, "onVerificationCompleted:$credential")
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                Log.w(TAG, "onVerificationFailed", e)
//                println("FAILED")
//                println(e.message)
//                progressDialog.dismiss()
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                Log.d(TAG, "onCodeSent:$verificationId")
//                Toast.makeText(
//                    this@ReceiptActivity,
//                    "OTP sent, check your message",
//                    Toast.LENGTH_SHORT
//                ).show()
//                mVerificationId = verificationId
//                forceResendingToken = token
//                progressDialog.dismiss()
//            }
//        }

    //    fun startPhoneNumberVerification(phone: String) {
//        progressDialog.setTitle("Sending OTP code...")
//        progressDialog.show()
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(phone)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(mCallBacks!!)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    fun resendPhoneNumberVerification(phone: String, token: PhoneAuthProvider.ForceResendingToken) {
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(phone)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(mCallBacks!!)
//            .setForceResendingToken(token)// OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        progressDialog.setTitle("Verifying Code...")
//        progressDialog.show()
//        firebaseAuth.signInWithCredential(credential)
//            .addOnSuccessListener {
////                var database = FirebaseDatabase.getInstance().getReference("FlowerCart")
////                database.removeValue()
//                Toast.makeText(this@ReceiptActivity, "Payment successful", Toast.LENGTH_SHORT)
//                    .show()
//                startActivity(Intent(this, HomeActivity::class.java))
//                var userAuth = firebaseAuth.currentUser
//            }.addOnFailureListener {
//                progressDialog.dismiss()
//                Toast.makeText(this, "The OTP you entered is not correct", Toast.LENGTH_SHORT)
//                    .show()
//            }
//    }
//
//    fun sendOtpCode(verifiID: String, strOTP: String) {
//        progressDialog.setTitle("Verifying Code...")
//        progressDialog.show()
//
//        val credential = PhoneAuthProvider.getCredential(verifiID, strOTP)
//        signInWithPhoneAuthCredential(credential)
//    }

}
