package com.koalagames.playfair.goapp.purpose

import android.webkit.CookieManager
import android.webkit.WebView

infix fun CookieManager.setAccepts(value: Pair<WebView?, Boolean>) {
    setAcceptCookie(value.second)
    setAcceptThirdPartyCookies(value.first, value.second)
}