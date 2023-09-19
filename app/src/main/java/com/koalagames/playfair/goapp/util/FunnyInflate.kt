package com.koalagames.playfair.goapp.util

import android.view.LayoutInflater
import android.view.ViewGroup

infix fun LayoutInflater.inf(layoutId: Int) = this to layoutId

infix fun Pair<LayoutInflater, Int>.inf(container: ViewGroup?) = first.inflate(second, container, false)