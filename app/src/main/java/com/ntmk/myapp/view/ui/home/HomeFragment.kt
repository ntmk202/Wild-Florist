package com.ntmk.myapp.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentHomeBinding
import com.ntmk.myapp.adapters.CategoriesAdapter
import com.ntmk.myapp.adapters.ListFlowerHomeAdapter
import com.ntmk.myapp.model.ListCgrData
import com.ntmk.myapp.adapters.ListHomeAdapter
import com.ntmk.myapp.model.Flower
import com.ntmk.myapp.model.ListHomeData
import com.ntmk.myapp.model.User

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var mDatabase: DatabaseReference
    private lateinit var flowerList: ArrayList<Flower>
    private lateinit var mAdapter: ListFlowerHomeAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //    add data list
    private var Cgr_data = mutableListOf<ListCgrData>()
    private var lH_data = mutableListOf<ListHomeData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        flowerList = ArrayList()
        mAdapter = ListFlowerHomeAdapter(flowerList)
        binding.listProduct.layoutManager = LinearLayoutManager(activity)
        binding.listProduct.setHasFixedSize(true)
        binding.listProduct.adapter = mAdapter
        getFlowerData()



//      add list
        postToListCgr()
        postToListHome()

//      RecycleView
        val rvCategoriesList : RecyclerView = binding.categories
        val rvHomeListItem : RecyclerView = binding.listProduct

//      Layout
        rvCategoriesList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvCategoriesList.adapter = CategoriesAdapter(Cgr_data)

        rvHomeListItem.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        rvHomeListItem.adapter = ListHomeAdapter(lH_data)

        return root
    }
    fun getFlowerData(){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    //    list item home
    private fun postToListHome(){
        lH_data.add(ListHomeData("Name Flower 1","$10",R.drawable.list_flower))
        lH_data.add(ListHomeData("Name Flower 2","$16",R.drawable.list_flower))
        lH_data.add(ListHomeData("Name Flower 3","$10",R.drawable.list_flower))
    }
}