package com.koalagames.playfair.goapp.purpose

import androidx.activity.result.ActivityResultLauncher

infix fun ActivityResultLauncher<String>.launchWithPermission(permission: String) {
    launch(permission)
}