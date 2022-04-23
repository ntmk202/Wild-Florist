package com.ntmk.myapp.ui.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentLiveBinding
import com.ntmk.myapp.ui.live.adapter.ListLiveAdapter
import com.ntmk.myapp.ui.live.adapter.ListLiveData

class LiveFragment : Fragment() {

    private var _binding: FragmentLiveBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //    add data list
    private var Live_data = mutableListOf<ListLiveData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLiveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        postToListHome()

//      RecycleView
        val rvLiveList : RecyclerView = binding.ListLive

//        layout
        rvLiveList.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        rvLiveList.adapter = ListLiveAdapter(Live_data)

        return root
    }

    private fun postToListHome() {
        Live_data.add(ListLiveData(R.drawable.garden_live,R.drawable.user,"Pot 1","#"+"tag 1"))
        Live_data.add(ListLiveData(R.drawable.garden_live,R.drawable.user,"Pot 2","#"+"tag 2"))
        Live_data.add(ListLiveData(R.drawable.garden_live,R.drawable.user,"Pot 3","#"+"tag 3"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}