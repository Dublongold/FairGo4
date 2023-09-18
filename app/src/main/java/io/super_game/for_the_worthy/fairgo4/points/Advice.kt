package io.super_game.for_the_worthy.fairgo4.points

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.allViews
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.inf
import io.super_game.for_the_worthy.fairgo4.util.leave

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