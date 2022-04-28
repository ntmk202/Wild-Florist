package com.ntmk.myapp.view.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ntmk.myapp.R
import com.ntmk.myapp.view.SettingActivity
import com.ntmk.myapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val setting : ImageView = binding.linkSetting
        val linkBalance : TextView = binding.linkBalance

        setting.setOnClickListener {
            val i = Intent(activity, SettingActivity::class.java)
            startActivity(i)
        }
        linkBalance.setOnClickListener {
            val fragment = BalanceFragment()
            val transaction =fragmentManager?.beginTransaction()
            transaction?.replace(R.id.navigation_balance,fragment)?.commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}