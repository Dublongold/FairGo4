package com.koalagames.playfair.goapp.purpose

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

fun Uri.setForBoth(values: Pair<(Uri) -> Unit, Intent>) {
    values.first(this)
    values.second.putExtra(MediaStore.EXTRA_OUTPUT,this)
}