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
import com.example.safesite.databinding.FragmentUpdateBinding
import com.example.safesite.penalty.database.Penalty
import com.example.safesite.penalty.database.PenaltyDatabase
import com.example.safesite.penalty.database.PenaltyRepository
import com.example.safesite.penalty.viewmodel.UpdateViewModel
import com.example.safesite.penalty.viewmodel.UpdateViewModelFactory


class UpdateFragment : Fragment() {

    private lateinit var updateViewModel: UpdateViewModel
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_update, container, false)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update, container, false
        )

        val application = requireNotNull(this.activity).application
        val dao = PenaltyDatabase.getInstance(application).penaltyDatabaseDao
        val repository = PenaltyRepository(dao)
        val factory = UpdateViewModelFactory(repository)
        updateViewModel = ViewModelProvider(this, factory).get(UpdateViewModel::class.java)
        binding.myViewModel = updateViewModel
        binding.lifecycleOwner = this

        updateViewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

            }
        })

        binding.btnSearch.setOnClickListener { search() }
        return binding.root
    }

    private fun search(){
        if (binding.btnSearch.text == "Search penalty here") {
            val penaltyDate = updateViewModel.penaltyDate.value.toString()

            initRecyclerView(penaltyDate)
        }

        if (binding.btnSearch.text == "Update"){
            val penaltyDate = updateViewModel.penaltyDate.value.toString()
            updateViewModel.update()
            initRecyclerView(penaltyDate)
            binding.btnSearch.text = "Search"
        }
    }

    private fun initRecyclerView(penaltyDate: String) {
        binding.penaltyRecyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MyRecyclerViewAdapter { selectedItem: Penalty -> listItemClicked(selectedItem) }
        binding.penaltyRecyclerView.adapter = adapter
        displayPenaltyList(penaltyDate)
    }

    private fun displayPenaltyList(penaltyDate: String) {
        updateViewModel.search(penaltyDate).observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(penalty: Penalty) {
        updateViewModel.initUpdate(penalty)
        binding.btnSearch.text= "Update"

    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title="Edit Penalty"
    }
}