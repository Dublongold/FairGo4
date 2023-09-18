package io.super_game.for_the_worthy.fairgo4.purpose

import android.content.Intent
import android.net.Uri

fun Intent.getDataStringAsUri() =
    if(dataString != null) {
        val uri = Uri.parse(dataString)
        arrayOf(uri)
    }
    else {
        null
    }
