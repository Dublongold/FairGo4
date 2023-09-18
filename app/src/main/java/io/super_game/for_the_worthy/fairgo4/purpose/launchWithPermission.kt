package io.super_game.for_the_worthy.fairgo4.purpose

import androidx.activity.result.ActivityResultLauncher

infix fun ActivityResultLauncher<String>.launchWithPermission(permission: String) {
    launch(permission)
}