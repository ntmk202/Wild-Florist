package com.ntmk.myapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ntmk.myapp.R
import com.ntmk.myapp.controller.CartFirebase
import com.ntmk.myapp.databinding.ZListCartTabViewBinding
import com.ntmk.myapp.databinding.ZListOrderBinding
import com.ntmk.myapp.databinding.ZListOrderBindingImpl
import com.ntmk.myapp.model.Flower
import com.ntmk.myapp.model.FlowerCart
import com.ntmk.myapp.model.Order

class OrderAdapter(var context: Context, var listOrder: ArrayList<Order>) :
    RecyclerView.Adapter<OrderAdapter.listOrderViewHolder>() {
    var mContext: Context? = context

    inner class listOrderViewHolder(var v: ZListOrderBinding) :
        RecyclerView.ViewHolder(v.root) {
        var rView: RecyclerView? = null
        var txtTimeOrder: TextView? = null
        var txtOrderTotal: TextView? = null

        public  lateinit var mListFlowerCart: ArrayList<FlowerCart>
        public lateinit var mAdapter: ReceiptAdapter

        init {
            txtTimeOrder = v.txtTime
            txtOrderTotal = v.txtMoney
            rView = v.flowerOrder
            mListFlowerCart = ArrayList()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listOrderViewHolder {
        val infler = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ZListOrderBinding>(
            infler, R.layout.z_list_order, parent, false
        )
        return listOrderViewHolder(v)
    }

    override fun onBindViewHolder(holder: listOrderViewHolder, position: Int) {
        holder.v.order = listOrder[position]
        var order: Order = listOrder[position]
        holder.mListFlowerCart = order.listFlower!!
        holder.mAdapter = ReceiptAdapter(holder.mListFlowerCart)
        holder.rView!!.layoutManager = LinearLayoutManager(context)
        holder.rView!!.setHasFixedSize(true)
        holder.rView!!.adapter = holder.mAdapter

    }

    override fun getItemCount(): Int {
        return listOrder.size
    }
}