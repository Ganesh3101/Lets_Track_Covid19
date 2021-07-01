package com.letstrackcovid.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_cowin_web.*
import kotlinx.android.synthetic.main.activity_webview.*

class Cowin_web : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cowin_web)

        cowin_web.webViewClient = WebViewClient()
        
        cowin_web.loadUrl("https://www.cowin.gov.in/home")
        cowin_web.settings.javaScriptEnabled = true


        cowin_web.settings.setSupportZoom(true)
    }
}