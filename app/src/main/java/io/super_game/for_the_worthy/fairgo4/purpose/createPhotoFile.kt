package io.super_game.for_the_worthy.fairgo4.purpose

import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import java.io.File

fun AppCompatActivity.createPhotoFile(): File =
    File.createTempFile(
        PHOTO_FILE_PREFIX,
        PHOTO_FILE_SUFFIX,
        getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )