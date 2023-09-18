package io.super_game.for_the_worthy.fairgo4.purpose

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

fun Uri.setForBoth(values: Pair<(Uri) -> Unit, Intent>) {
    values.first(this)
    values.second.putExtra(MediaStore.EXTRA_OUTPUT,this)
}