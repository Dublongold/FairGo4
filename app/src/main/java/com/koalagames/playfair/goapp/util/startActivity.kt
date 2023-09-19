package com.koalagames.playfair.goapp.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startActivity(activity: Class<*>) {
    startActivity(Intent(this, activity))
}