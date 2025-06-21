package com.schoolproject.droidshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.data.remote.auth.FirebaseAuthService
import com.schoolproject.droidshop.domain.model.User
import com.schoolproject.droidshop.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
     private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val authState: StateFlow<Resource<User>> = _authState.asStateFlow()


    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn.asStateFlow()


    init {
        // On VM init, immediately check Firebase’s persisted session
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _isUserLoggedIn.value = user != null
        }
    }

    fun signUp(email: String, password: String, username: String) = viewModelScope.launch {

        _authState.value = Resource.Loading()
        when(val response = authRepository.signUp(email,password,username)) {
            is Resource.Error -> {
                _authState.value = Resource.Error("Sign Up Error")
                _isUserLoggedIn.value = false
            }
            is Resource.Loading -> {
                _authState.value = Resource.Loading()
            }
            is Resource.Success -> {
                _authState.value = Resource.Success(response.data)
                _isUserLoggedIn.value = true
            }

            is Resource.Idle -> {}
        }

    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _authState.value = Resource.Loading()
        _authState.value = authRepository.signIn(email,password)
    }


    fun signOut() = viewModelScope.launch {
        authRepository.signOut()
//            when(val response = authRepository.signOut()) {
//                is Resource.Error -> {
//                    _authState.value = Resource.Error(response.message.toString())
//                }
//                is Resource.Loading -> {
//                    _authState.value = Resource.Loading()
//                }
//                is Resource.Success -> {
//                    _isUserLoggedIn.value = false
//                }
//
//                is Resource.Idle -> {}
//            }

        }

}