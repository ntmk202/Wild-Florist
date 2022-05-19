package com.ntmk.myapp

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ntmk.myapp.adapters.FlowerCartAdapter
import com.ntmk.myapp.adapters.ReceiptAdapter
import com.ntmk.myapp.databinding.ActivityCartBinding
import com.ntmk.myapp.databinding.ActivityReceiptBinding
import com.ntmk.myapp.model.FlowerCart
import com.ntmk.myapp.model.User
import com.ntmk.myapp.view.CartActivity
import com.ntmk.myapp.view.HomeActivity

class ReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiptBinding
    private lateinit var mListFlowerCart: ArrayList<FlowerCart>
    private lateinit var mAdapter: ReceiptAdapter
    lateinit var mDatabase: DatabaseReference
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


        binding.btnConfirm.setOnClickListener {
            val v = View.inflate(this, R.layout.verify_email_fragment, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(v)
            val dialog = builder.create()

            v.findViewById<View>(R.id.btn_submit).setOnClickListener {
                var txtNum: EditText = v.findViewById<View>(R.id.verifyOTP) as EditText

                var number: Int = txtNum.text.toString().toInt()

                if (number == 456789) {
                    mDatabase.removeValue()
                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Incorrect code", Toast.LENGTH_SHORT).show()
                    txtNum.setText("")

                }


                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,

                )
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.window?.setGravity(Gravity.BOTTOM)

        }
    }

    fun init(){
        getAddressUser()
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
        var number: String = ""
        number = string.replace("\$", "")
        println(number)
        return number.toInt()
    }
    fun getAddressUser() {
        var address = ""
        var database = FirebaseDatabase.getInstance().getReference("Users")
        var list_user = ArrayList<User>()
        var userAuth = FirebaseAuth.getInstance().currentUser
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children){
                    var user = data.getValue(User::class.java)
                    list_user.add(user as User)
                }
                var emailAuth = userAuth?.email
                for(user in list_user){
                    if(user.email.equals(emailAuth)){
                        binding.txtAddress.setText(user.address)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel",error.toString())
            }
        })
    }

}