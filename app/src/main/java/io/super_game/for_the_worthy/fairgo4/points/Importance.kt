package io.super_game.for_the_worthy.fairgo4.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.super_game.for_the_worthy.fairgo4.R
import io.super_game.for_the_worthy.fairgo4.util.inf
import io.super_game.for_the_worthy.fairgo4.util.leave

class Importance: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater inf R.layout.importance inf container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatImageButton>(R.id.home_button).setOnClickListener {
            findNavController().leave()
        }
    }
}
