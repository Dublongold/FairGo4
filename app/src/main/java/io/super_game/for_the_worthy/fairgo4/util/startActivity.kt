package io.super_game.for_the_worthy.fairgo4.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startActivity(activity: Class<*>) {
    startActivity(Intent(this, activity))
}