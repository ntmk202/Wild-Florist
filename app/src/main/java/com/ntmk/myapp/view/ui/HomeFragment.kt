package com.ntmk.myapp.view.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.ntmk.myapp.R
import com.ntmk.myapp.adapters.CategoriesAdapter
import com.ntmk.myapp.adapters.ListFlowerHomeAdapter
import com.ntmk.myapp.databinding.FragmentHomeBinding
import com.ntmk.myapp.model.Flower
import com.ntmk.myapp.model.ListCgrData
import com.ntmk.myapp.view.CartActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var mDatabase: DatabaseReference
    private lateinit var flowerList: ArrayList<Flower>
    private lateinit var mAdapter: ListFlowerHomeAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.


    //    add data list
    private var Cgr_data = mutableListOf<ListCgrData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        flowerList = ArrayList()
        mAdapter = ListFlowerHomeAdapter(requireContext(), flowerList)
        binding.listProduct.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.listProduct.setHasFixedSize(true)
        binding.listProduct.adapter = mAdapter

        getFlowerData()

        binding.imgCart.setOnClickListener {
            val i = Intent(requireContext(), CartActivity::class.java)
            startActivity(i)
        }
        binding.txtSearch.setOnClickListener {
            showDialogSearch()
        }

//      add list
        postToListCgr()

//      Layout
        binding.categories.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.categories.adapter = CategoriesAdapter(Cgr_data)


        return binding.root
    }

    fun getFlowerData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Flowers")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (data in p0.children) {
                        val flower = data.getValue(Flower::class.java)
                        flowerList.add(flower!!)

                    }
                    binding.listProduct.adapter = mAdapter
                    binding.listProduct.adapter?.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Cancel", error.toString())
            }
        })
    }
    private fun showDialogSearch() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_search)
        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttribute = window.attributes
        windowAttribute.gravity = Gravity.TOP

//        EditText editText = dialog.findViewById(R.id.layout);
        dialog.show()
    }

    //    list categories horizontal
    private fun postToListCgr() {
        Cgr_data.add(ListCgrData("FRESH", R.drawable.flower_item1))
        Cgr_data.add(ListCgrData("DRIED", R.drawable.flower_dried))
        Cgr_data.add(ListCgrData("ORNAMENT", R.drawable.flower_pot))
        Cgr_data.add(ListCgrData("FESTIVAL", R.drawable.flowers_festival))
        Cgr_data.add(ListCgrData("SEEDS", R.drawable.seed))
        Cgr_data.add(ListCgrData("VASES", R.drawable.vase))
        Cgr_data.add(ListCgrData("TOOLS", R.drawable.gardening_tools))
    }

}