package com.koalagames.playfair.goapp.purpose

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
