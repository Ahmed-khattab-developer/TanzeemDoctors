package com.tanzeem.tanzeemDoctors.repository

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.tanzeem.tanzeemDoctors.data.model.Doctor
import com.tanzeem.tanzeemDoctors.utils.Resource
import com.tanzeem.tanzeemDoctors.utils.networkState.AuthState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class AuthRepository
@Inject
constructor() {

    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    private val doctorModel = "doctor"

    suspend fun register(doctor: Doctor) = flow {
        emit(Resource.Loading())
        try {
            val id = fireStoreDatabase.collection(doctorModel).document().id
            fireStoreDatabase.collection(doctorModel)
                .document(id).set(doctor).await()
            emit(Resource.Success(id))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(e.localizedMessage ?: "Check Your Internet Connection")
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    suspend fun getAllDoctorsData() = flow {
        emit(Resource.Loading())
        try {
            val snapshot = fireStoreDatabase.collection(doctorModel)
                .orderBy("nameArabic")
                .get().await()
            val list: ArrayList<Doctor> = ArrayList()
            for (document in snapshot.documents) {
                val doctor: Doctor? = document.toObject(Doctor::class.java)
                list.add(doctor!!)
            }
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(e.localizedMessage ?: "Check Your Internet Connection")
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

    suspend fun getDoctorsData(search: String) = flow {
        emit(Resource.Loading())
        try {
            val snapshot = fireStoreDatabase.collection(doctorModel)
                .orderBy("nameArabic")
                .startAt(search.trim())
                .endAt(search.trim() + "\uf8ff")
                .get().await()
            val list: ArrayList<Doctor> = ArrayList()
            for (document in snapshot.documents) {
                val doctor: Doctor? = document.toObject(Doctor::class.java)
                list.add(doctor!!)
            }
            emit(Resource.Success(list))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(
                Resource.Error(e.localizedMessage ?: "Check Your Internet Connection")
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }

}