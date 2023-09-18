package io.super_game.for_the_worthy.fairgo4.purpose

import io.super_game.for_the_worthy.fairgo4.PurposePoint

fun PurposePoint.doIfFilePathCallbackNotNull(action: PurposePoint.() -> Unit) {
    if(filePathCallback != null) {
        action()
        filePathCallback = null
    }
}