package com.example.safesite.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.safesite.R

import com.example.safesite.attendance.AttendanceMainActivity
import com.example.safesite.databinding.FragmentHomeBinding
import com.example.safesite.location.LocationActivity
import com.example.safesite.record.RecordMainActivity
import com.example.safesite.todo.TodoMainActivity


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home,container, false
        )
        /*If clicked the Attendance button in home,this will redirect
        to attendance activity
         */
        binding.btnHomeAttendance.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), RecordMainActivity::class.java)
            startActivity(intent)
        }

/*
        binding.penalty.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_home_to_penaltyFragment)
        }

        binding.btnHomeTodo.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), TodoMainActivity::class.java)
            startActivity(intent)
        }
 */

        /*If clicked the Location button in home,this will redirect
        to location activity
        */
        binding.btnHomeLocation.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), LocationActivity::class.java)
            startActivity(intent)
        }

        //add this for options menu
        setHasOptionsMenu(true)

        return binding.root
    }

    //add this for options menu
    //'main' is the xml file name of options menu/overflow menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}
