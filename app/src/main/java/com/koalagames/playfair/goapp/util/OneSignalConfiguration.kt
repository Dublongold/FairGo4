package com.koalagames.playfair.goapp.util

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.onesignal.OneSignal
import kotlinx.coroutines.launch

object OneSignalConfiguration {
    fun configure(context: Context, lifecycleScope: LifecycleCoroutineScope) {
        OneSignal.initWithContext(context, APP_ID)
        lifecycleScope.launch {
            OneSignal.Notifications.requestPermission(true)
        }
    }

    const val APP_ID = "68b4e6f4-4055-45e1-b1da-b0626aace4a2"
}