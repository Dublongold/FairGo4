package com.koalagames.playfair.goapp.points

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.allViews
import androidx.fragment.app.DialogFragment
import com.koalagames.playfair.goapp.R
import com.koalagames.playfair.goapp.util.inf

class Advice: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.advice inf container
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            attributes?.also { attributes ->
                attributes.dimAmount = 0f
                this.attributes = attributes
            }
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        view.apply {
            allViews.filter {
                it is FrameLayout || it is AppCompatImageButton
            }.forEach {
                it.setOnClickListener {
                    dialog?.dismiss()
                }
            }
        }
    }
}