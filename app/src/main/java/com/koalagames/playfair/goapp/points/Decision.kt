package com.koalagames.playfair.goapp.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koalagames.playfair.goapp.R
import com.koalagames.playfair.goapp.util.inf

class Decision: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.decision inf container
    }
}