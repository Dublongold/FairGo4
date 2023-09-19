package com.koalagames.playfair.goapp

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.koalagames.playfair.goapp.util.OneSignalConfiguration
import com.koalagames.playfair.goapp.util.itIsFail
import com.koalagames.playfair.goapp.util.startActivity


class MainPoint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OneSignalConfiguration.configure(applicationContext, lifecycleScope)
        setContentView(R.layout.main_point)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

    override fun onResume() {
        super.onResume()
        FirebaseApp.initializeApp(this)

        Firebase.remoteConfig.apply {
            reset().addOnCompleteListener {
                fetchAndActivate().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val (allow, url) = getBoolean("trusted") to getString("destination")
                        if (allow && url.contains("/") && url.contains(":") && url.contains("http")) {
                            startActivity(PurposePoint::class.java)
                            finish()
                            Log.i("Firebase", "Good! ($url)")
                        } else {
                            Log.i("Firebase", "Bad... ($allow, $url)")
                            findNavController(R.id.points).itIsFail()
                        }
                    } else {
                        Log.i("Firebase", "Failed...")
                        findNavController(R.id.points).itIsFail()
                    }
                }
            }
        }
    }
}