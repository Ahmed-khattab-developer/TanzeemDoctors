package com.tanzeem.tanzeemDoctors.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tanzeem.tanzeemDoctors.R
import com.tanzeem.tanzeemDoctors.data.model.Doctor
import com.tanzeem.tanzeemDoctors.databinding.ItemDoctorBinding
import com.tanzeem.tanzeemDoctors.ui.DoctorDetailsActivity

class DoctorAdapter(val context: Context) : RecyclerView.Adapter<LargeNewsViewHolder>() {

    private lateinit var dataBinding: ItemDoctorBinding
    private var doctors: ArrayList<Doctor> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LargeNewsViewHolder {
        dataBinding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LargeNewsViewHolder(context, dataBinding)
    }

    override fun onBindViewHolder(holder: LargeNewsViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bind(doctor)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DoctorDetailsActivity::class.java)
            intent.putExtra("doctor", doctor)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (doctors.isNotEmpty())
            doctors.size
        else
            0
    }

    fun updateDoctorList(newDoctors: ArrayList<Doctor>) {
        doctors.clear()
        doctors.addAll(newDoctors)
        notifyDataSetChanged()
    }
}

class LargeNewsViewHolder(val context: Context, private val dataBinding: ItemDoctorBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {
    fun bind(doctor: Doctor) {
        dataBinding.tvDoctorName.text = doctor.nameArabic
        dataBinding.tvDoctorTitle.text = doctor.title
        dataBinding.tvDoctorFaculty.text = doctor.faculty

        try {
            Picasso.get()
                .load(modifyDropboxUrl(doctor.image[0]))
                .placeholder(AppCompatResources.getDrawable(context, R.drawable.doctor)!!)
                .into(dataBinding.ivDoctorImage)
        } catch (ex: Exception) {
            dataBinding.ivDoctorImage.setImageDrawable(
                AppCompatResources.getDrawable(context, R.drawable.doctor)
            )
        }


    }

    private fun modifyDropboxUrl(originalUrl: String): String {
        var newUrl = originalUrl.replace("www.dropbox.", "dl.dropboxusercontent.")
        newUrl = newUrl.replace("dropbox.", "dl.dropboxusercontent.")
        return newUrl
    }
}