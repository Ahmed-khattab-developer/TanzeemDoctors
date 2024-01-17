package com.tanzeem.tanzeemDoctors.utils.appUtils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.tanzeem.tanzeemDoctors.R
import com.tanzeem.tanzeemDoctors.databinding.NoInternetDialogBinding

class NoInternetDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: NoInternetDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.no_internet_dialog, null, false
        )
        setContentView(binding.root)
    }

}