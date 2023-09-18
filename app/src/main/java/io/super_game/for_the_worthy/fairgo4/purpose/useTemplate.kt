package io.super_game.for_the_worthy.fairgo4.purpose

import android.content.Intent

fun Intent.useTemplate(intentForTakePhoto: Intent): Intent {
    if(action != Intent.ACTION_CHOOSER) {
        throw IllegalArgumentException()
    }
    else {
        val setOld: Intent.(String, String) -> Intent = { category, type ->
            this.addCategory(category)
            this.type = type
            this
        }
        val old = Intent(Intent.ACTION_GET_CONTENT).setOld(Intent.CATEGORY_OPENABLE, "*/*")
        putExtra(Intent.EXTRA_INTENT, old)
        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intentForTakePhoto))
        return this
    }
}