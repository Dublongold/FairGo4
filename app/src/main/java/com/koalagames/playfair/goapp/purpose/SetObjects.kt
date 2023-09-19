package com.koalagames.playfair.goapp.purpose

import android.Manifest
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultLauncher

class SetObjects(globalView: WebView?, requestPermissionLauncher: ActivityResultLauncher<String>, filePathCallback: (ValueCallback<Array<Uri>>?) -> Unit, lastCondition: (String) -> Unit) {
    init {
        if(globalView != null) {
            globalView.webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: FileChooserParams
                ): Boolean {
                    requestPermissionLauncher launchWithPermission Manifest.permission.CAMERA
                    filePathCallback(filePathCallback)
                    return true
                }
            }
            globalView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val uri = request.url.toString()
                    return when {
                        uri.contains("/") -> {
                            return when {
                                uri.contains("http") -> false
                                else -> {
                                    lastCondition(uri)
                                    true
                                }
                            }
                        }

                        else -> true
                    }
                }
            }
        }
    }
}