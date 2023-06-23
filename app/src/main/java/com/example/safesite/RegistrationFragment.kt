package com.example.safesite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.safesite.database.RegisterDatabase
import com.example.safesite.database.RegisterRepository
import com.example.safesite.databinding.FragmentRegistrationBinding
import com.example.safesite.model.AddViewModel
import com.example.safesite.model.AddViewModelFactory
import com.example.safesite.profile.Interface
import com.example.safesite.profile.ProfileActivity

class RegistrationFragment : Fragment() {

    private lateinit var registerViewModel: AddViewModel
    private lateinit var binding: FragmentRegistrationBinding

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_registration,container,false)

/*
        val name = view?.findViewById<EditText>(R.id.regUsername)
        val btn = view?.findViewById<Button>(R.id.submitButton)
        val Interface: Interface = activity as Interface

 */

        val application = requireNotNull(this.activity).application
        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val repository = RegisterRepository(dao)
        val factory = AddViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(AddViewModel::class.java)
        binding.myViewModel = registerViewModel
        binding.lifecycleOwner = this

/*
        btn?.setOnClickListener{
            val msg = name?.text.toString()
            // val name = regUsername.text.toString()
            // val intent = Intent(requireContext(), ProfileActivity::class.java)
            // intent.putExtra("xx", name)
            // startActivity(intent)
            Interface.transferredMessage(msg)
        }

 */
        /*
        registerViewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()

            }
        })
         */

        registerViewModel.navigateto.observe(this, Observer { hasFinished ->
            if (hasFinished == true) {
                Log.i("MYTAG", "inside observe")
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(this, Observer {
            Log.i("MYTAG", it.toString()+"000000000000000000000000")
        })

        registerViewModel.errortoast.observe(this, Observer { hasError->
            if (hasError==true){
                Toast.makeText(requireContext(), "Please fill the fields!", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })

        registerViewModel.errortoastUsername.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Username already taken!", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })

        //back button register to login if want to back in login screen without registering
       // (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false
        return binding.root
    }

    //additional
    private fun displayUsersList() {
        Log.i("MYTAG","inside ")
        this.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
//        NavHostFragment.findNavController(this).navigate()
    }

    //action bar
    override fun onResume() {
        super.onResume()
        //set title
        (requireActivity() as MainActivity).supportActionBar?.title="Register"
    }
}