package com.ntmk.myapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ntmk.myapp.databinding.ZListItemHomeViewBinding
import androidx.recyclerview.widget.RecyclerView
import com.ntmk.myapp.R
import com.ntmk.myapp.model.Flower

class ListFlowerHomeAdapter(var flowerList:ArrayList<Flower>)
    :RecyclerView.Adapter<ListFlowerHomeAdapter.ListFlowerHomeViewHolder>()
{

    inner class ListFlowerHomeViewHolder(var v:ZListItemHomeViewBinding) : RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFlowerHomeViewHolder {
        val infler = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ZListItemHomeViewBinding>(
            infler, R.layout.z_list_item_home_view,parent,false
        )
        return  ListFlowerHomeViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListFlowerHomeViewHolder, position: Int) {
        holder.v.listItemHome = flowerList[position]
    }

    override fun getItemCount(): Int {
        return  flowerList.size
    }
}