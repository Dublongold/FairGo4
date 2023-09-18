package io.super_game.for_the_worthy.fairgo4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import io.super_game.for_the_worthy.fairgo4.util.itIsFail
import io.super_game.for_the_worthy.fairgo4.util.startActivity


class MainPoint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_point)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onResume() {
        super.onResume()
        FirebaseApp.initializeApp(this)

        val conf = Firebase.remoteConfig.apply {
            reset().addOnCompleteListener {
                fetchAndActivate().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val (allow, url) = getBoolean("allow") to getString("url")
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