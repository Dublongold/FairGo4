package io.super_game.for_the_worthy.fairgo4.purpose

import android.webkit.CookieManager
import android.webkit.WebView

infix fun CookieManager.setAccepts(value: Pair<WebView?, Boolean>) {
    setAcceptCookie(value.second)
    setAcceptThirdPartyCookies(value.first, value.second)
}