package com.example.safesite.penalty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.safesite.MainActivity
import com.example.safesite.R
import com.example.safesite.databinding.FragmentDisplayBinding
import com.example.safesite.penalty.database.Penalty
import com.example.safesite.penalty.database.PenaltyDatabase
import com.example.safesite.penalty.database.PenaltyRepository
import com.example.safesite.penalty.viewmodel.DisplayViewModel
import com.example.safesite.penalty.viewmodel.DisplayViewModelFactory


class DisplayFragment : Fragment() {
    private lateinit var displayViewModel: DisplayViewModel
    private lateinit var binding: FragmentDisplayBinding
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_display, container, false)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_display, container, false
        )
        val application = requireNotNull(this.activity).application

        val dao = PenaltyDatabase.getInstance(application).penaltyDatabaseDao
        val repository = PenaltyRepository(dao)
        val factory = DisplayViewModelFactory(repository)
        displayViewModel = ViewModelProvider(this, factory).get(DisplayViewModel::class.java)
        binding.myViewModel = displayViewModel
        binding.lifecycleOwner = this

        displayViewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

            }
        })
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.penaltyRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MyRecyclerViewAdapter { selectedItem: Penalty -> listItemClicked(selectedItem) }
        binding.penaltyRecyclerView.adapter = adapter
        displayPenaltyList()
    }

    private fun displayPenaltyList() {
        displayViewModel.getSavedPenalty().observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(penalty: Penalty) {
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title="Penalties"
    }

}