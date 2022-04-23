package com.ntmk.myapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ntmk.myapp.R

class ListHomeAdapter (private var VH_List: List<ListHomeData>):
    RecyclerView.Adapter<ListHomeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val LVH_Name: TextView = itemView.findViewById(R.id.txtView_list)
        val LVH_Price: TextView = itemView.findViewById(R.id.txtPrice_list)
        val LVH_Image: ImageView = itemView.findViewById(R.id.imgView_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHomeAdapter.ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.z_list_item_home_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var LH_data = VH_List[position]
        holder.LVH_Name.text = LH_data.listVH_name
        holder.LVH_Price.text = LH_data.listVH_price
        holder.LVH_Image.setImageResource(LH_data.listVH_image)
    }

    override fun getItemCount(): Int {
        return VH_List.size
    }
}