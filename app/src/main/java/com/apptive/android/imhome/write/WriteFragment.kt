package com.apptive.android.imhome.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apptive.android.imhome.baseClass.BaseFragment
import com.apptive.android.imhome.databinding.FragmentWriteBinding

class WriteFragment: BaseFragment() {

    private lateinit var binding:FragmentWriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWriteBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}