package com.koalagames.playfair.goapp.util

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.koalagames.playfair.goapp.R

fun NavController.advice() = navigate(R.id.get_advice)

fun NavController.itIsFail() = navigate(R.id.start_familiarization)

fun NavController.choice() = navigate(R.id.you_need_choose)

fun NavController.importance() = navigate(R.id.read_importance)

fun NavController.leave() = popBackStack()

fun NavController.fate(level: Int) {
    popBackStack(R.id.choice, false)
    navigate(R.id.meet_fate, bundleOf("level" to level))
}

fun NavController.result(time: Int, score: Int) = navigate(R.id.what_next, bundleOf(
    "time" to time,
    "score" to score
))

fun NavController.backChoose() {
    popBackStack(R.id.choice, false)
}

fun Fragment.getResult(): Pair<Int, Int> {
    val time = arguments?.getInt("time") ?: -1
    val score = arguments?.getInt("score") ?: -1
    return time to score
}

fun Fragment.getFateLevel() = arguments?.getInt("level") ?: -1