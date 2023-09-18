package io.super_game.for_the_worthy.fairgo4.util

import java.io.File

class FatesFileManager(private val filesDirection: File) {
    private var completedFates = 0
    fun getFile() = File(filesDirection, "fates.txt")

    fun getCompletedFates(): Int {
        val file = getFile()
        return if(needUpdate) {
            completedFates = if(file.exists()) {
                file.readText().toIntOrNull() ?: 0
            }
            else {
                0
            }
            completedFates
        }
        else {
            completedFates
        }
    }

    fun setCompletedFates(value: Int) {
        needUpdate = true
        val file = getFile()
        file.writeText(value.toString())
    }

    companion object {
        var needUpdate = true
    }
}