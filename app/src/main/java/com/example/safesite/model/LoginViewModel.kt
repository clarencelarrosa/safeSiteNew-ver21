package com.example.safesite.model

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.safesite.database.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: RegisterRepository, application: Application): ViewModel(), Observable{

    val users=repository.users

    @Bindable
    val username = MutableLiveData<String>()
    @Bindable
    val password = MutableLiveData<String>()

    private val viewModelJob = Job()
    private val uiScope= CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigatetoRegister = MutableLiveData<Boolean>()
    val navigatetoRegister: LiveData<Boolean>
        get() =  _navigatetoRegister

    private val _navigatetoUserDetails = MutableLiveData<Boolean>()
    val navigatetoUserDetails: LiveData<Boolean>
        get() = _navigatetoUserDetails

    private val _errorToast = MutableLiveData<Boolean>()
    val errortoast: LiveData<Boolean>
        get() = _errorToast

    private val _errorToastUsername = MutableLiveData<Boolean>()
    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername

    private val _errorToastInvalidPassword = MutableLiveData<Boolean>()
    val errorToastInvalidPassword: LiveData<Boolean>
        get() = _errorToastInvalidPassword


    fun signUP() {
        _navigatetoRegister.value = true
    }


    @SuppressLint("NullSafeMutableLiveData")
    fun loginButton() {
        if (username.value == null || password.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(username.value!!)
                if (usersNames != null) {
                    if(usersNames.password == password.value){
                        username.value = null
                        password.value = null
                        _navigatetoUserDetails.value = true
                    }else{
                        _errorToastInvalidPassword.value = true
                    }
                } else {
                    _errorToastUsername.value = true
                }
            }
        }
    }

    fun doneNavigatingRegister() {
        _navigatetoRegister.value = false
    }

    fun doneNavigatingUserDetails() {
        _navigatetoUserDetails.value = false
    }

    fun donetoast() {
        _errorToast.value = false
        Log.i("MYTAG", "Done toasting ")
    }

    fun donetoastErrorUsername() {
        _errorToastUsername .value = false
        Log.i("MYTAG", "Done toasting ")
    }

    fun donetoastInvalidPassword() {
        _errorToastInvalidPassword .value = false
        Log.i("MYTAG", "Done toasting ")
    }

    //Necessary since the class needs to be abstract
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}