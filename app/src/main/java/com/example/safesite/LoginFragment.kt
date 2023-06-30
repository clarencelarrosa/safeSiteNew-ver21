package com.example.safesite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.safesite.database.RegisterDatabase
import com.example.safesite.database.RegisterRepository
import com.example.safesite.databinding.FragmentLoginBinding
import com.example.safesite.model.LoginViewModel
import com.example.safesite.model.LoginViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login,container,false)
        //(activity as MainActivity).hideBottomNav()

        val application = requireNotNull(activity).application
        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        val repository = RegisterRepository(dao)
        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.myViewModel = loginViewModel
        binding.lifecycleOwner = this

        loginViewModel.navigatetoRegister.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","inside observe")
                displayUsersList()
                loginViewModel.doneNavigatingRegister()
            }
        })

        loginViewModel.errortoast.observe(this, Observer { hasError->
            if(hasError==true) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername .observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "User doesn't exist. Click 'Register'", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorUsername()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "Invalid Password! Please check and retype your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })


        loginViewModel.navigatetoUserDetails.observe(this, Observer { hasFinished->
            if(hasFinished == true) {
                Log.i("MYTAG", "Inside Observe")
                //displayUsersList()
                navigateUserDetails()
                loginViewModel.doneNavigatingUserDetails()
            }
        })

        //(requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //Redirect to register screen
        binding.btnRegister.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return binding.root
    }

    private fun displayUsersList() {
        Log.i("MYTAG", "inside display users list")
        this.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun navigateUserDetails() {
        Log.i("MYTAG", "Inside home page")
        this.findNavController().navigate(R.id.action_loginFragment_to_nav_home)
    }

    //action bar
    override fun onResume() {
        super.onResume()
        //title of the action bar
        (requireActivity() as MainActivity).supportActionBar?.title="Login"
        //back button (hide)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //(requireActivity() as MainActivity).supportActionBar?.hide()
    }

/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view=requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        view.visibility = View.GONE
    }
    override fun onDetach() {
        super.onDetach()
    (activity as MainActivity).showBottomNav()
    }

     */

}