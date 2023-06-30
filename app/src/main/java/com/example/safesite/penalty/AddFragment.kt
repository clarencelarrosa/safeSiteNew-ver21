package com.example.safesite.penalty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.safesite.MainActivity
import com.example.safesite.R
import com.example.safesite.databinding.FragmentAddBinding
import com.example.safesite.penalty.database.PenaltyDatabase
import com.example.safesite.penalty.database.PenaltyRepository
import com.example.safesite.penalty.viewmodel.AddPenaltyViewModel
import com.example.safesite.penalty.viewmodel.AddPenaltyViewModelFactory


class AddFragment : Fragment() {

    private lateinit var penaltyViewModel: AddPenaltyViewModel
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add,container,false)
        val application = requireNotNull(this.activity).application

        val dao = PenaltyDatabase.getInstance(application).penaltyDatabaseDao
        val repository = PenaltyRepository(dao)
        val factory = AddPenaltyViewModelFactory(repository)

        penaltyViewModel = ViewModelProvider(this, factory).get(AddPenaltyViewModel::class.java)
        binding.myViewModel = penaltyViewModel
        binding.lifecycleOwner = this

        penaltyViewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

            }

        })
        return binding.root

    }

    //setting the title of the action bar
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title="Add Penalty"
    }
}