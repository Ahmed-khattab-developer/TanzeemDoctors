package com.tanzeem.tanzeemDoctors.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanzeem.tanzeemDoctors.data.model.Doctor
import com.tanzeem.tanzeemDoctors.repository.AuthRepository
import com.tanzeem.tanzeemDoctors.utils.Resource
import com.tanzeem.tanzeemDoctors.utils.networkState.AuthState
import com.tanzeem.tanzeemDoctors.utils.networkState.DoctorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor
    (private var authRepository: AuthRepository) : ViewModel() {

    private val _doctor = MutableStateFlow(AuthState())
    val doctor: StateFlow<AuthState> = _doctor

    private val _doctors = MutableStateFlow(DoctorState())
    val doctors: StateFlow<DoctorState> = _doctors

    private val _doctorsSearch = MutableStateFlow(DoctorState())
    val doctorsSearch: StateFlow<DoctorState> = _doctorsSearch


    fun register(doctor: Doctor) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.register(doctor).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _doctor.value = AuthState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _doctor.value = AuthState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        _doctor.value = AuthState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getAllDoctorsData() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.getAllDoctorsData().onEach {
                when (it) {
                    is Resource.Loading -> {
                        _doctors.value = DoctorState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _doctors.value = DoctorState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        _doctors.value = DoctorState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getDoctorsData(search :String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.getDoctorsData(search).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _doctorsSearch.value = DoctorState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _doctorsSearch.value = DoctorState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        _doctorsSearch.value = DoctorState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}