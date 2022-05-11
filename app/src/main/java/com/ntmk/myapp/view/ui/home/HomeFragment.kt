package com.ntmk.myapp.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentHomeBinding
import com.ntmk.myapp.adapters.CategoriesAdapter
import com.ntmk.myapp.adapters.ListFlowerHomeAdapter
import com.ntmk.myapp.model.ListCgrData
import com.ntmk.myapp.model.Flower
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    //    add variable data
    private var Cgr_data = mutableListOf<ListCgrData>()
    lateinit var mDatabase: DatabaseReference
    private lateinit var flowerList: ArrayList<Flower>
    private lateinit var mAdapter: ListFlowerHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


//      add list data
        postToListCgr()
        getFlowerData()

//      Layout categories
        binding.categories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categories.adapter = CategoriesAdapter(Cgr_data)
//      layout flower list
        flowerList = ArrayList()
        mAdapter = ListFlowerHomeAdapter(flowerList)
        binding.listProduct.layoutManager = GridLayoutManager(activity,2, GridLayoutManager.VERTICAL, false)
        binding.listProduct.setHasFixedSize(true)
        binding.listProduct.adapter = mAdapter


        return binding.root
    }

    private fun getFlowerData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Flowers")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for(data in p0.children){
                        val flower = data.getValue(Flower::class.java)
                        flowerList.add(flower!!)
                    }
                    binding.listProduct.adapter = mAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel",error.toString())
            }
        })
    }

    //    list categories horizontal
    private fun postToListCgr(){
        Cgr_data.add(ListCgrData("FRESH", R.drawable.flower_item1))
        Cgr_data.add(ListCgrData("DRIED", R.drawable.flower_dried))
        Cgr_data.add(ListCgrData("ORNAMENT", R.drawable.flower_pot))
        Cgr_data.add(ListCgrData("FESTIVAL", R.drawable.flowers_festival))
        Cgr_data.add(ListCgrData("SEEDS", R.drawable.seed))
        Cgr_data.add(ListCgrData("VASES", R.drawable.vase))
        Cgr_data.add(ListCgrData("TOOLS", R.drawable.gardening_tools))
    }


}