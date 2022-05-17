package com.ntmk.myapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.adapters.FlowerCartAdapter
import com.ntmk.myapp.adapters.ListFlowerHomeAdapter
import com.ntmk.myapp.databinding.ActivityCartBinding
import com.ntmk.myapp.model.FlowerCart

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var mListFlowerCart: ArrayList<FlowerCart>
    private lateinit var mAdapter: FlowerCartAdapter
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mListFlowerCart = ArrayList()
        mAdapter = FlowerCartAdapter(this, mListFlowerCart)
        binding.flowerCart.layoutManager = LinearLayoutManager(this)
        binding.flowerCart.setHasFixedSize(true)
        binding.flowerCart.adapter = mAdapter
        getFlowerData()

        binding.linkBack.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }


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