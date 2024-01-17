package com.tanzeem.tanzeemDoctors.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanzeem.tanzeemDoctors.R
import com.tanzeem.tanzeemDoctors.data.model.Doctor
import com.tanzeem.tanzeemDoctors.databinding.ActivityMainBinding
import com.tanzeem.tanzeemDoctors.ui.adapter.DoctorAdapter
import com.tanzeem.tanzeemDoctors.utils.appUtils.NoInternetDialog
import com.tanzeem.tanzeemDoctors.viewModel.AuthViewModel
import com.tanzeem.tanzeemDoctors.viewModel.ConnectionViewModel
import com.thekhaeng.pushdownanim.PushDownAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataBinding: ActivityMainBinding
    private val connectionViewModel: ConnectionViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private var noInternetDialog: NoInternetDialog? = null
    private lateinit var doctorAdapter: DoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        noInternetDialog = NoInternetDialog(this)
        noInternetDialog!!.setCancelable(false)
        noInternetDialog!!.window?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent))
        )
        connectionViewModel.isOnline.observe(this) { isOnline ->
            if (isOnline) {
                noInternetDialog!!.hide()
            } else {
                noInternetDialog!!.show()
            }
        }

        doctorAdapter = DoctorAdapter(this)
        dataBinding.rvDoctor.adapter = doctorAdapter
        dataBinding.rvDoctor.layoutManager = LinearLayoutManager(this)


        PushDownAnim.setPushDownAnimTo(dataBinding.addDoctor)
            .setScale(PushDownAnim.MODE_SCALE, 0.85f).setOnClickListener(this)

        authViewModel.getAllDoctorsData()

        dataBinding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    s!!.isEmpty() -> {
                        authViewModel.getAllDoctorsData()
                    }

                    else -> {
                        authViewModel.getDoctorsData(s.toString().trim())
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    override fun onClick(v: View?) {
        if (v == dataBinding.addDoctor) {
            val doctor = Doctor(
                "", "", "", "", "",
                "", "", ArrayList(), ""
            )
            authViewModel.register(doctor)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                authViewModel.doctor.collect {
                    if (it.isLoading) {
                        dataBinding.animationLoading.visibility = View.VISIBLE
                    }
                    if (it.error.isNotBlank()) {
                        dataBinding.animationLoading.visibility = View.GONE
                    }
                    it.data?.let {
                        dataBinding.animationLoading.visibility = View.GONE
                        Toast.makeText(
                            this@MainActivity, getString(R.string.done), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                authViewModel.doctors.collect {
                    if (it.isLoading) {
                        dataBinding.animationLoading.visibility = View.VISIBLE
                    }
                    if (it.error.isNotBlank()) {
                        dataBinding.animationLoading.visibility = View.GONE
                        Log.e("aaaaaaaaaaa", it.error)
                    }
                    it.data?.let { arrayListDoctor ->
                        doctorAdapter.updateDoctorList(arrayListDoctor)
                        Log.d("Listtttttttttt", it.toString())
                        dataBinding.animationLoading.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                authViewModel.doctorsSearch.collect {
                    if (it.isLoading) {
                        dataBinding.animationLoading.visibility = View.VISIBLE
                    }
                    if (it.error.isNotBlank()) {
                        dataBinding.animationLoading.visibility = View.GONE
                        Log.e("aaaaaaaaaaa", it.error)
                    }
                    it.data?.let { arrayListDoctor ->
                        doctorAdapter.updateDoctorList(arrayListDoctor)
                        Log.d("Listtttttttttt", it.toString())
                        dataBinding.animationLoading.visibility = View.GONE
                    }
                }
            }
        }
    }
}