package com.ntmk.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ntmk.myapp.R
import com.ntmk.myapp.model.ListCgrData

class CategoriesAdapter (private var Cgr_List: List<ListCgrData>):
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val cgrTitles: TextView = itemView.findViewById(R.id.txtV_categories)
        val cgrImages: ImageView = itemView.findViewById(R.id.imgV_categories)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.z_categories_item_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cgr_List = Cgr_List[position]
        holder.cgrTitles.text = cgr_List.titlesCgr_List
        holder.cgrImages.setImageResource(cgr_List.imagesCgr_List)
    }

    override fun getItemCount(): Int {
        return Cgr_List.size
    }
}