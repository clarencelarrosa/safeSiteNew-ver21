package com.example.safesite.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.*
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.safesite.MainActivity
import com.example.safesite.R
import com.example.safesite.databinding.FragmentLoginBinding
import com.example.safesite.databinding.FragmentSettingBinding
import kotlinx.parcelize.Parcelize



class SettingFragment : Fragment() {

   private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSettingBinding.inflate(layoutInflater)
        binding.aboutSettingsButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
/*
        binding.aboutFaqButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_faqFragment2)
        }
 */
        binding.settingsBack.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_settingsFragment_to_nav_home)
        }
        return binding.root
    }

}