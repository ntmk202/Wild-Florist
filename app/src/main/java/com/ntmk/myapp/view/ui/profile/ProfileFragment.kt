package com.ntmk.myapp.view.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        val settingFragment = SettingFragment()
        binding.linkSetting.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_profile, settingFragment, SettingFragment::class.java.simpleName)
                    .addToBackStack(null).commit()
            }
        }

        val balanceFragment = BalanceFragment()
        binding.linkBalance.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.nav_profile, balanceFragment, BalanceFragment::class.java.simpleName)
                    .addToBackStack(null).commit()
            }
        }

        return binding.root
    }
}