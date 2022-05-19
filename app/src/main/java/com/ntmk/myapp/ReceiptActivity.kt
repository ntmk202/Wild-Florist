package com.ntmk.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
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
                var txtNum1: EditText = v.findViewById<View>(R.id.num1) as EditText
                var txtNum2: EditText = v.findViewById<View>(R.id.num2) as EditText
                var txtNum3: EditText = v.findViewById<View>(R.id.num3) as EditText
                var txtNum4: EditText = v.findViewById<View>(R.id.num4) as EditText
                var txtNum5: EditText = v.findViewById<View>(R.id.num5) as EditText
                var txtNum6: EditText = v.findViewById<View>(R.id.num6) as EditText
                var num1: String = txtNum1.text.toString()
                var num2: String = txtNum2.text.toString()
                var num3: String = txtNum3.text.toString()
                var num4: String = txtNum4.text.toString()
                var num5: String = txtNum5.text.toString()
                var num6: String = txtNum6.text.toString()

                code(txtNum1,txtNum2,txtNum3,txtNum4,txtNum5,txtNum6)

                var number = (num1 + num2 + num3 + num4 + num5 + num6).toInt()
                if (number == 456789) {
                    mDatabase.removeValue()
                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Incorrect code", Toast.LENGTH_SHORT).show()
                    txtNum1.setText("")
                    txtNum2.setText("")
                    txtNum3.setText("")
                    txtNum4.setText("")
                    txtNum5.setText("")
                    txtNum6.setText("")
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
    fun code(num1 : EditText,num2 : EditText,num3 : EditText,num4 : EditText,num5 : EditText,num6 : EditText){
        num1.addTextChangedListener(object : TextWatcher {
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
                num2.requestFocus()
            }
        })
    }
}