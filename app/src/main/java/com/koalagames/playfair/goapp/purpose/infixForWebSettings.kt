package com.koalagames.playfair.goapp.purpose

import android.webkit.WebView

infix fun WebView.setBoolean(value: Boolean) {
    settings.apply {
        val booleans = listOf(
            ::setAllowContentAccess,
            ::setAllowContentAccess,
            ::setAllowFileAccess,
            ::setJavaScriptCanOpenWindowsAutomatically,
            ::setAllowFileAccessFromFileURLs,
            ::setDomStorageEnabled,
            ::setJavaScriptEnabled,
            ::setDatabaseEnabled,
            ::setAllowUniversalAccessFromFileURLs,
            ::setUseWideViewPort,
            ::setLoadWithOverviewMode
        )
        for(boolean in booleans) {
            boolean(value)
        }
    }
}

infix fun WebView.mixedContentMode(value: Int) {
    settings.mixedContentMode = value
}

infix fun WebView.cacheMode(value: Int) {
    settings.cacheMode = value
}

infix fun WebView.removeFromUserAgent(replace: String) {
    settings.userAgentString = settings.userAgentString.replace(replace, "")
}