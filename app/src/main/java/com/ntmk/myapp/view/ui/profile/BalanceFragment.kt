package com.ntmk.myapp.view.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentBalanceBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class BalanceFragment : Fragment() {

    private lateinit var binding: FragmentBalanceBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_balance, container, false)


//        val profileFragment = ProfileFragment()
//        binding.linkBack.setOnClickListener {
//            fragmentManager?.beginTransaction()?.apply {
//                replace(R.id.nav_profile, profileFragment, ProfileFragment::class.java.simpleName)
//                    .addToBackStack(null).commit()
//            }
//            fragmentManager?.saveBackStack(
//                FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN.toString()
//            )
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linkBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

//    companion object {
//        @JvmStatic
//        fun newInstance() =
//            ProfileFragment().apply {
//                arguments = Bundle().apply {
//                }
//            }
//    }



}