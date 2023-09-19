package com.koalagames.playfair.goapp.purpose

import com.koalagames.playfair.goapp.PurposePoint

fun PurposePoint.doIfFilePathCallbackNotNull(action: PurposePoint.() -> Unit) {
    if(filePathCallback != null) {
        action()
        filePathCallback = null
    }
}