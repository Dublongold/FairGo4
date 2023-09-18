package io.super_game.for_the_worthy.fairgo4.purpose

import android.net.Uri
import android.webkit.ValueCallback

class ReceiveMainUriCallback(mainUriCallback: Uri?, filePathCallback: ValueCallback<Array<Uri>>?) {
    init {
        if(filePathCallback != null) {
            if (mainUriCallback != null) {
                filePathCallback.onReceiveValue(arrayOf(mainUriCallback))
            } else {
                filePathCallback.onReceiveValue(null)
            }
        }
    }
}