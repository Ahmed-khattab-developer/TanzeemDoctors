package com.tanzeem.tanzeemDoctors.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    var nameArabic: String = "",
    var nameEnglish: String = "",
    var phone: String = "",
    var phone2: String = "",
    var email: String = "",
    var faculty: String = "",
    var title: String = "",
    var image: List<String> = ArrayList(),
    var gender: String = ""
):Parcelable