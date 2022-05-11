package com.ntmk.myapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart )
    }
}