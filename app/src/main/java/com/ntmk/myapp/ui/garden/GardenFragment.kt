package com.ntmk.myapp.ui.garden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentGardenBinding
import com.ntmk.myapp.ui.garden.adapter.ListGardenAdapter
import com.ntmk.myapp.ui.garden.adapter.ListGardenData

class GardenFragment : Fragment() {

    private var _binding: FragmentGardenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //    add data list
    private var Garden_data = mutableListOf<ListGardenData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGardenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        postToListHome()

//      RecycleView
        val rvGardenList : RecyclerView = binding.listGarden

//        layout
        rvGardenList.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        rvGardenList.adapter = ListGardenAdapter(Garden_data)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //    list item garden
    private fun postToListHome(){
        Garden_data.add(ListGardenData("Name Flower 1","10°C", "98%", "15%", R.drawable.list_flower))
        Garden_data.add(ListGardenData("Name Flower 2","20°C", "78%","50%",R.drawable.list_flower))
        Garden_data.add(ListGardenData("Name Flower 3","20°C", "80%","27%",R.drawable.list_flower))
    }
}