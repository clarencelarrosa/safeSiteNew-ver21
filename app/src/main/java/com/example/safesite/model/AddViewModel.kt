package com.example.safesite.model

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safesite.MainActivity
import com.example.safesite.database.Register
import com.example.safesite.database.RegisterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddViewModel (private val repository: RegisterRepository, application: Application) : ViewModel(), Observable {

    private var userdata: String? = null
    var userDetailsLiveData = MutableLiveData<Array<Register>>()

    @Bindable
    val firstName = MutableLiveData<String>()

    @Bindable
    val lastName = MutableLiveData<String>()

    @Bindable
    val username = MutableLiveData<String>()

    @Bindable
    val password = MutableLiveData<String>()

    init{
        firstName.value=""
        lastName.value=""
        username.value = ""
        password.value = ""
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //navigate
    private val _navigateto = MutableLiveData<Boolean>()
    val navigateto: LiveData<Boolean>
        get() = _navigateto

    //error toast
    private val _errorToast = MutableLiveData<Boolean>()
    val errortoast: LiveData<Boolean>
        get() = _errorToast

    //error toast for username
    private val _errorToastUsername = MutableLiveData<Boolean>()
    val errortoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun submitButton(){
        Log.i("MYTAG", "INSIDE THE SAVE BUTTON")
        if (firstName.value == null || lastName.value == null || username.value == null || password.value == null) {
            _errorToast.value = true
        } else {
            uiScope.launch {
                val usersnames = repository.getUserName(username.value!!)
                Log.i("MYTAG", usersnames.toString() + "------------------")
                if (usersnames != null) {
                    _errorToastUsername.value = true
                    Log.i("MYTAG", "Inside if not null")
                } else {
                    Log.i("MYTAG", userDetailsLiveData.value.toString() + "asdfghjklmnop")
                    Log.i("MYTAG", "OK! I'm in")
                    //ORIG
                    val firstnameval = firstName.value!!
                    val lastnameval = lastName.value!!
                    val usernameval = username.value!!
                    val passwordval = password.value!!
                    Log.i("MYTAG", "inside submit")
                    insert(Register(0, firstnameval,lastnameval,usernameval, passwordval))
                    firstName.value=""
                    lastName.value=""
                    username.value = ""
                    password.value = ""
                    _navigateto.value = true
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
        Log.i("MYTAG", "Done navigating ")
    }

    fun donetoast() {
        _errorToast.value = false
        Log.i("MYTAG", "Done toasting ")
    }

    fun donetoastUserName() {
        _errorToast.value = false
        Log.i("MYTAG", "Done toasting username")
    }

    //ORIGINAL
    private fun insert(register: Register): Job = viewModelScope.launch {
        repository.insert(register)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}
