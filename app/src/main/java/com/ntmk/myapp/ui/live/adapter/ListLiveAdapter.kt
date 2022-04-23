package com.ntmk.myapp.ui.live.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ntmk.myapp.R

class ListLiveAdapter (private var Live_List: List<ListLiveData>):
    RecyclerView.Adapter<ListLiveAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val LImage_list: ImageView = itemView.findViewById(R.id.imgLive_list)
        val Live_name: TextView = itemView.findViewById(R.id.tvNameGarden)
        val Live_tag: TextView = itemView.findViewById(R.id.tvTagGarden)
        val Live_image: ImageView = itemView.findViewById(R.id.imgGardenName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLiveAdapter.ViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.z_list_live_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val LiveData = Live_List[position]
        holder.LImage_list.setImageResource(LiveData.LImage_list)
        holder.Live_image.setImageResource(LiveData.Live_img)
        holder.Live_name.text = LiveData.Live_name
        holder.Live_tag.text = LiveData.Live_tag
    }

    override fun getItemCount(): Int {
        return Live_List.size
    }
}