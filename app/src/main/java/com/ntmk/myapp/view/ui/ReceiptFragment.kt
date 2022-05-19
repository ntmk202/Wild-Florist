package com.ntmk.myapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ntmk.myapp.R
import com.ntmk.myapp.databinding.FragmentProfileBinding
import com.ntmk.myapp.databinding.FragmentReceiptBinding

class ReceiptFragment : Fragment() {

    private lateinit var binding: FragmentReceiptBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt, container, false)


        return binding.root
    }

}