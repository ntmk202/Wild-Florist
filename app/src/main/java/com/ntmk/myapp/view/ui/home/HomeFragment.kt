package com.ntmk.myapp.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentHomeBinding
import com.ntmk.myapp.adapters.CategoriesAdapter
import com.ntmk.myapp.adapters.ListFlowerHomeAdapter
import com.ntmk.myapp.model.ListCgrData
import com.ntmk.myapp.databinding.ZListItemHomeViewBinding
import com.ntmk.myapp.model.Flower
import com.ntmk.myapp.view.CartActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var mDatabase: DatabaseReference
    private lateinit var flowerList: ArrayList<Flower>
    private lateinit var mAdapter: ListFlowerHomeAdapter
    private lateinit var mbinding: ZListItemHomeViewBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    //    add data list
    private var Cgr_data = mutableListOf<ListCgrData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //mbinding = DataBindingUtil.setContentView(requireActivity(), R.layout.z_list_item_home_view)

        mbinding =  ZListItemHomeViewBinding.inflate(inflater, container, false)
        flowerList = ArrayList()
        mAdapter = ListFlowerHomeAdapter(requireContext(),flowerList)


        binding.listProduct.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.listProduct.setHasFixedSize(true)
        binding.listProduct.adapter = mAdapter

        getFlowerData()

        mbinding.lyViewListPink.setOnClickListener {
            println("CLICKED")
        }
        mbinding.txtViewList.setOnClickListener {
            println("CLICKED!!!!")
        }

        binding.imgCart.setOnClickListener {
            val i = Intent(context, CartActivity::class.java)
            startActivity(i)
        }

//      add list
        postToListCgr()

//      Layout
        binding.categories.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categories.adapter = CategoriesAdapter(Cgr_data)


        return binding.root
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
                    binding.listProduct.adapter?.notifyDataSetChanged()

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

}