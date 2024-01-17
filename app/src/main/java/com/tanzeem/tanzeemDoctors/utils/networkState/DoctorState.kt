package com.tanzeem.tanzeemDoctors.utils.networkState

import com.tanzeem.tanzeemDoctors.data.model.Doctor

data class DoctorState(
    val data: ArrayList<Doctor>? = null,
    val error: String = "",
    val isLoading: Boolean = false
)