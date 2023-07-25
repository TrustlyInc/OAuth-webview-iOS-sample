package net.trustly.inappbrowserandroid.webchromeclient

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.browser.customtabs.CustomTabsIntent
import net.trustly.inappbrowserandroid.TrustlyConstants

@SuppressLint("SetJavaScriptEnabled")
class TrustlyWebView : LinearLayout {

    var webView: WebView = WebView(context!!)

    constructor(context: Context?) : super(context, null)

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        with(webView) {
            this.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            this.layoutParams =
                RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

            this.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val url = request.url.toString()
                    if (url.contains(TrustlyConstants.OAUTH_LOGIN_PATH))
                        launchUrl(view.context, url)
                    return true
                }
            }
        }
    }

    fun proceedToChooseAccount() {
        webView.loadUrl(TrustlyConstants.TRUSTLY_PROCEED_TO_CHOOSE_ACCOUNT_SCRIPT)
    }

    private fun launchUrl(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

}