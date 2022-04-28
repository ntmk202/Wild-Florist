package com.ntmk.myapp.view.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentBalanceBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class BalanceFragment : Fragment() {

    private var _binding: FragmentBalanceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBalanceBinding.inflate(inflater, container, false)
        return binding.root

        val linkBack : ImageButton = binding.linkBack
        linkBack.setOnClickListener {
            val fragment = BalanceFragment()
            val transaction =fragmentManager?.beginTransaction()
            transaction?.replace(R.id.navigation_profile,fragment)?.commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}