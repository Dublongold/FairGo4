@file:Suppress("DEPRECATION")

package com.koalagames.playfair.goapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.koalagames.playfair.goapp.purpose.ReceiveMainUriCallback
import com.koalagames.playfair.goapp.purpose.SetObjects
import com.koalagames.playfair.goapp.purpose.cacheMode
import com.koalagames.playfair.goapp.purpose.createPhotoFile
import com.koalagames.playfair.goapp.purpose.doIfFilePathCallbackNotNull
import com.koalagames.playfair.goapp.purpose.getDataStringAsUri
import com.koalagames.playfair.goapp.purpose.mixedContentMode
import com.koalagames.playfair.goapp.purpose.removeFromUserAgent
import com.koalagames.playfair.goapp.purpose.setAccepts
import com.koalagames.playfair.goapp.purpose.setBoolean
import com.koalagames.playfair.goapp.purpose.setForBoth
import com.koalagames.playfair.goapp.purpose.useTemplate
import java.io.File
import java.io.IOException


class PurposePoint : AppCompatActivity() {
    private var globalWeb: WebView? = null
    var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var mainUriCallback: Uri? = null
    private val enableOnBackPressedCallback = true
    private var beginnerUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.purpose_point)
        globalWeb = findViewById(R.id.globalWeb)
        globalWeb?.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        beginnerUrl = Firebase.remoteConfig.getString("destination")
        webViewConfiguration()
        if(beginnerUrl != "") {
            globalWeb!!.loadUrl(beginnerUrl)
        }
        else {
            throw IllegalArgumentException("Url is null...")
        }
    }

    override fun onStart() {
        super.onStart()
        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(enableOnBackPressedCallback) {
            override fun handleOnBackPressed() {
                if(globalWeb?.canGoBack() == true) {
                    globalWeb?.goBack()
                }
            }
        })
    }

    private fun webViewConfiguration() {
        globalWeb!! setBoolean true
        globalWeb!! mixedContentMode WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        globalWeb!! cacheMode WebSettings.LOAD_DEFAULT
        globalWeb!! removeFromUserAgent "; wv"
        CookieManager.getInstance() setAccepts (globalWeb to true)
        SetObjects(globalWeb, singleActivityResultLauncher, {
            filePathCallback = it
        }) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    }

    private val singleActivityResultLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _: Boolean? ->
        var exception: Exception? = null
        val photoFile: File? = try {
            createPhotoFile()
        } catch (e: IOException) {
            exception = e
            null
        }
        if(exception != null) {
            Log.e("Photo file creation", "Exception during creation file.", exception)
        }
        else {
            Log.i("Photo file creation", "File created successful.")
        }
        val intentForTakePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        Uri.fromFile(photoFile).setForBoth({uri: Uri -> mainUriCallback = uri} to intentForTakePhoto)
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        startActivityForResult(chooserIntent.useTemplate(intentForTakePhoto), 1)
    }

    @Deprecated("Deprecated in Java")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        doIfFilePathCallbackNotNull {
            if (resultCode == -1) {
                val dataStr = data?.getDataStringAsUri()
                if (dataStr != null) {
                    filePathCallback!!.onReceiveValue(dataStr)
                }
                else {
                    ReceiveMainUriCallback(mainUriCallback, filePathCallback)
                }
            } else {
                ReceiveMainUriCallback(null, filePathCallback)
            }
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        globalWeb?.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        globalWeb?.restoreState(savedInstanceState)
    }
}