package com.tanzeem.tanzeemDoctors.ui

import android.content.ClipData
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.tanzeem.tanzeemDoctors.R
import com.tanzeem.tanzeemDoctors.data.model.Doctor
import com.tanzeem.tanzeemDoctors.databinding.ActivityDoctorDetailsBinding
import com.tanzeem.tanzeemDoctors.ui.adapter.MyAdapter
import com.tanzeem.tanzeemDoctors.utils.appUtils.NoInternetDialog
import com.tanzeem.tanzeemDoctors.viewModel.ConnectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dataBinding: ActivityDoctorDetailsBinding
    private val connectionViewModel: ConnectionViewModel by viewModels()
    private var noInternetDialog: NoInternetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_details)
        setupUI()
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

        val doctor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("doctor", Doctor::class.java)
        } else {
            intent.getParcelableExtra("doctor")
        }

        dataBinding.doctorName.text = doctor?.nameArabic

        try {
            Picasso.get()
                .load(modifyDropboxUrl(doctor?.image!![0]))
                .placeholder(AppCompatResources.getDrawable(this, R.drawable.doctor)!!)
                .into(dataBinding.doctorImage)
        } catch (ex: Exception) {
            dataBinding.doctorImage.setImageDrawable(
                AppCompatResources.getDrawable(this, R.drawable.doctor)
            )
        }

        dataBinding.doctorNameArabic.text = doctor?.nameArabic
        dataBinding.doctorNameArabic.setOnClickListener(this)
        dataBinding.doctorNameEnglish.text = doctor?.nameEnglish
        dataBinding.doctorNameEnglish.setOnClickListener(this)
        dataBinding.doctorPhone.text = doctor?.phone
        dataBinding.doctorPhone.setOnClickListener(this)
        dataBinding.doctorPhoneTwo.text = doctor?.phone2
        dataBinding.doctorPhoneTwo.setOnClickListener(this)
        dataBinding.doctorEmail.text = doctor?.email
        dataBinding.doctorEmail.setOnClickListener(this)
        dataBinding.doctorFaculty.text = doctor?.faculty
        dataBinding.doctorFaculty.setOnClickListener(this)
        dataBinding.doctorTitle.text = doctor?.title
        dataBinding.doctorTitle.setOnClickListener(this)

        dataBinding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        dataBinding.myRecyclerView.adapter = MyAdapter(this, doctor!!.image)
    }

    override fun onClick(v: View?) {
        when (v) {
            dataBinding.doctorNameArabic -> {
                setClipboard(dataBinding.doctorNameArabic.text.toString())
            }

            dataBinding.doctorNameEnglish -> {
                setClipboard(dataBinding.doctorNameEnglish.text.toString())
            }

            dataBinding.doctorPhone -> {
                setClipboard(dataBinding.doctorPhone.text.toString())
            }

            dataBinding.doctorPhoneTwo -> {
                setClipboard(dataBinding.doctorPhoneTwo.text.toString())
            }

            dataBinding.doctorEmail -> {
                setClipboard(dataBinding.doctorEmail.text.toString())
            }

            dataBinding.doctorFaculty -> {
                setClipboard(dataBinding.doctorFaculty.text.toString())
            }

            dataBinding.doctorTitle -> {
                setClipboard(dataBinding.doctorTitle.text.toString())
            }
        }
        Toast.makeText(this, "تم النسخ بنجاح", Toast.LENGTH_LONG).show()
    }

    private fun setClipboard(text: String) {
        val clipboard =
            getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun modifyDropboxUrl(originalUrl: String): String {
        var newUrl = originalUrl.replace("www.dropbox.", "dl.dropboxusercontent.")
        newUrl = newUrl.replace("dropbox.", "dl.dropboxusercontent.")
        return newUrl
    }
}