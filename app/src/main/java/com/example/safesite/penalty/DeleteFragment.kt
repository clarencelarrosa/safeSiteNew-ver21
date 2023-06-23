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
import com.example.safesite.databinding.FragmentDeleteBinding
import com.example.safesite.penalty.database.Penalty
import com.example.safesite.penalty.database.PenaltyDatabase
import com.example.safesite.penalty.database.PenaltyRepository
import com.example.safesite.penalty.viewmodel.DeleteViewModel
import com.example.safesite.penalty.viewmodel.DeleteViewModelFactory


class DeleteFragment : Fragment() {
    private lateinit var binding: FragmentDeleteBinding
    private lateinit var deleteViewModel: DeleteViewModel
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_delete, container, false)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_delete, container, false)
        val application = requireNotNull(activity).application
        val dao = PenaltyDatabase.getInstance(application).penaltyDatabaseDao
        val repository = PenaltyRepository(dao)
        val factory = DeleteViewModelFactory(repository)
        deleteViewModel = ViewModelProvider(this, factory).get(DeleteViewModel::class.java)
        binding.myViewModel = deleteViewModel
        binding.lifecycleOwner = this
        deleteViewModel.message.observe (
            viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                }
            }
        )
        initRecyclerView()
        return binding.root
    }

    private fun displayPenaltyList() {
        deleteViewModel.getSavedPenalty().observe(viewLifecycleOwner, Observer{
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        binding.penaltyRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MyRecyclerViewAdapter { selectedItem: Penalty -> listItemClicked(selectedItem)}
        binding.penaltyRecyclerView.adapter = adapter
        displayPenaltyList()
    }

    private fun listItemClicked (penalty: Penalty) {
        deleteViewModel.deletePenalty(penalty)
        displayPenaltyList()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title="Delete Penalty"
    }
}