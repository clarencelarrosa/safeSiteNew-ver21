package com.example.safesite.faq

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.safesite.R
import com.example.safesite.databinding.FragmentAboutBinding
import com.example.safesite.databinding.FragmentFaqBinding

class FaqFragment : Fragment() {

    private lateinit var binding: FragmentFaqBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_faq, container, false)
        binding = FragmentFaqBinding.inflate(layoutInflater)

        binding.faqBack.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_faqFragment2_to_settingsFragment)

        }
        return binding.root
    }
}