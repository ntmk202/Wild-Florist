package com.ntmk.myapp.view.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ntmk.myapp.R
import com.ntmk.myapp.adapters.OrderAdapter
import com.ntmk.myapp.databinding.FragmentOrderBinding
import com.ntmk.myapp.model.Order


class OrderFragment : Fragment() {

    private lateinit var binding : FragmentOrderBinding
    private lateinit var mListOrder: ArrayList<Order>
    private lateinit var mAdapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        onClickStatusOrder()
        binding.linkBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        mListOrder = ArrayList()
        mAdapter = OrderAdapter(requireContext(),mListOrder)
        binding.recyclerOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerOrder.setHasFixedSize(true)
        binding.recyclerOrder.adapter = mAdapter

        getOrderData()

        return binding.root
    }
    private fun getOrderData() {
        var userId = FirebaseAuth.getInstance().currentUser?.uid!!
        var mDatabase = FirebaseDatabase.getInstance().getReference("FlowerOrder").child(userId)
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    mListOrder.removeAll(mListOrder)
                    for (data in p0.children) {
                        val order = data.getValue(Order::class.java)
                        mListOrder.add(order!!)
                    }

                    binding.recyclerOrder.adapter = mAdapter
                    binding.recyclerOrder.adapter?.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })

    }

    private fun onClickStatusOrder(){
        binding.orderToPay.setOnClickListener {
            StatusOrderFirst()
            binding.imgOrderToPay.setImageResource(R.drawable.wait_confirm)

            binding.tvOrderToPay.setTextColor(Color.parseColor("#FF6600"))
            binding.tvOrderToPay.setTypeface(binding.tvOrderToPay.getTypeface(), Typeface.BOLD);
        }
        binding.orderToShip.setOnClickListener {
            StatusOrderFirst()
            binding.imgOrderToShip.setImageResource(R.drawable.order_to_ship)

            binding.tvOrderToShip.setTextColor(Color.parseColor("#FF6600"))
            binding.tvOrderToShip.setTypeface(binding.tvOrderToShip.getTypeface(), Typeface.BOLD);
        }
        binding.orderReceive.setOnClickListener {
            StatusOrderFirst()
            binding.imgOrderRecevie.setImageResource(R.drawable.order_recevie)

            binding.tvOrderReceive.setTextColor(Color.parseColor("#FF6600"))
            binding.tvOrderReceive.setTypeface(binding.tvOrderReceive.getTypeface(), Typeface.BOLD);
        }
        binding.orderComplete.setOnClickListener {
            StatusOrderFirst()
            binding.imgOrderComplete.setImageResource(R.drawable.order_complete)

            binding.tvOrderComplete.setTextColor(Color.parseColor("#FF6600"))
            binding.tvOrderComplete.setTypeface(binding.tvOrderComplete.getTypeface(), Typeface.BOLD);
        }
        binding.orderCancel.setOnClickListener {
            StatusOrderFirst()
            binding.imgOrderCancel.setImageResource(R.drawable.order_cancel)

            binding.tvOrderCancel.setTextColor(Color.parseColor("#FF6600"))
            binding.tvOrderCancel.setTypeface(binding.tvOrderCancel.getTypeface(), Typeface.BOLD);
        }

    }

    private fun StatusOrderFirst(){
        binding.imgOrderToPay.setImageResource(R.drawable.wait_confirm_unchecked)
        binding.imgOrderToShip.setImageResource(R.drawable.order_to_ship_unchecked)
        binding.imgOrderRecevie.setImageResource(R.drawable.order_receive_unchecked)
        binding.imgOrderComplete.setImageResource(R.drawable.order_complete_unchecked)
        binding.imgOrderCancel.setImageResource(R.drawable.order_cancel_unchecked)

        binding.tvOrderToPay.setTextColor(Color.parseColor("#888888"))
        binding.tvOrderToPay.setTypeface(null, Typeface.NORMAL);
        binding.tvOrderToShip.setTextColor(Color.parseColor("#888888"))
        binding.tvOrderToShip.setTypeface(null, Typeface.NORMAL);
        binding.tvOrderReceive.setTextColor(Color.parseColor("#888888"))
        binding.tvOrderReceive.setTypeface(null, Typeface.NORMAL);
        binding.tvOrderComplete.setTextColor(Color.parseColor("#888888"))
        binding.tvOrderComplete.setTypeface(null, Typeface.NORMAL);
        binding.tvOrderCancel.setTextColor(Color.parseColor("#888888"))
        binding.tvOrderCancel.setTypeface(null, Typeface.NORMAL);
    }
}