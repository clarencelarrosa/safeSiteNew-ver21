package com.example.safesite.penalty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.safesite.MainActivity
import com.example.safesite.R

import com.example.safesite.databinding.FragmentPenaltyBinding


class PenaltyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentPenaltyBinding>(
            inflater,
            R.layout.fragment_penalty, container, false
        )

        binding.btnAdd.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_penaltyFragment_to_addFragment2)
        }
        binding.btnDelete.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_penaltyFragment_to_deleteFragment2)
        }

        binding.btnDisplay.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_penaltyFragment_to_displayFragment2)
        }

        binding.btnUpdate.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_penaltyFragment_to_updateFragment3)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title="Penalties"
    }
}