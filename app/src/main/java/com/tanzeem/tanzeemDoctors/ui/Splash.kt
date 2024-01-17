package com.tanzeem.tanzeemDoctors.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.tanzeem.tanzeemDoctors.R
import com.tanzeem.tanzeemDoctors.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Splash : AppCompatActivity() {
    private lateinit var dataBinding: ActivitySplashBinding
    private var progressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        startLoading()
    }

    override fun onResume() {
        super.onResume()
        startLoading()
    }

    private fun startLoading() {
        Thread {
            while (progressStatus < 100) {
                progressStatus += 5
                Handler(Looper.getMainLooper()).post {
                    dataBinding.progressBar.progress = progressStatus
                    if (progressStatus == 100) {
                        val intent = Intent(this@Splash, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}