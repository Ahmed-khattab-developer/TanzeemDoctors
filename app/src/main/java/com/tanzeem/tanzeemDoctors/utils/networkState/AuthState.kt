package com.tanzeem.tanzeemDoctors.utils.networkState

data class AuthState(
    val data: String? = null,
    val error: String = "",
    val isLoading: Boolean = false
)