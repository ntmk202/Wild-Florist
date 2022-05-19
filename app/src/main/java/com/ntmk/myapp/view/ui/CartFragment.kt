package com.ntmk.myapp.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.adapters.FlowerCartAdapter
import com.ntmk.myapp.databinding.ActivityCartBinding
import com.ntmk.myapp.databinding.FragmentCartBinding
import com.ntmk.myapp.model.FlowerCart
import com.ntmk.myapp.view.HomeActivity
import com.ntmk.myapp.view.ui.profile.BalanceFragment

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mListFlowerCart: ArrayList<FlowerCart>
    private lateinit var mAdapter: FlowerCartAdapter
    lateinit var mDatabase: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        mListFlowerCart = ArrayList()
        mAdapter = FlowerCartAdapter(requireContext(), mListFlowerCart)
        binding.flowerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.flowerCart.setHasFixedSize(true)
        binding.flowerCart.adapter = mAdapter
        getFlowerData()

        binding.linkBack.setOnClickListener {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }
        binding.btnBuy.setOnClickListener {
            val v = View.inflate(requireContext(), R.layout.z_viewer_payment, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(v)
            val dialog = builder.create()

            v.findViewById<View>(R.id.pay_credit_card).setOnClickListener {
                val balanceFragment = BalanceFragment()
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.nav_cart, balanceFragment, BalanceFragment::class.java.simpleName)
                        .addToBackStack(null).commit()
                }
                val v = View.inflate(requireContext(), R.layout.verify_email_fragment, null)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(v)
                val dialog = builder.create()

                v.findViewById<View>(R.id.btn_submit).setOnClickListener {
                    var txtNum1 : EditText = v.findViewById<View>(R.id.num1) as EditText
                    var txtNum2 : EditText = v.findViewById<View>(R.id.num2) as EditText
                    var txtNum3 : EditText = v.findViewById<View>(R.id.num3) as EditText
                    var txtNum4 : EditText = v.findViewById<View>(R.id.num4) as EditText
                    var txtNum5 : EditText = v.findViewById<View>(R.id.num5) as EditText
                    var txtNum6 : EditText = v.findViewById<View>(R.id.num6) as EditText
                    var num1 : String = txtNum1.text.toString()
                    var num2 : String = txtNum2.text.toString()
                    var num3 : String = txtNum3.text.toString()
                    var num4 : String = txtNum4.text.toString()
                    var num5 : String = txtNum5.text.toString()
                    var num6 : String = txtNum6.text.toString()

                    var number = (num1+num2+num3+num4+num5+num6).toInt()
                    if(number == 456789){
                        Toast.makeText(requireContext(), "Payment success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Incorrect code", Toast.LENGTH_SHORT).show()
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
            v.findViewById<View>(R.id.pay_in_cash).setOnClickListener {
                Toast.makeText(requireContext(), "Successfully created orders", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.window?.setGravity(Gravity.BOTTOM)
        }


        return binding.root
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
}