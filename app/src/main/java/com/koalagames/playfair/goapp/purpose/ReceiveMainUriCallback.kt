package com.koalagames.playfair.goapp.purpose

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