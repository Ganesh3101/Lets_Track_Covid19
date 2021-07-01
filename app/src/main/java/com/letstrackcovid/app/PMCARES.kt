package com.letstrackcovid.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_cowin_web.*
import kotlinx.android.synthetic.main.activity_p_m_c_a_r_e_s.*

class PMCARES : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p_m_c_a_r_e_s)

        pm_cares.webViewClient = WebViewClient()

        pm_cares.loadUrl("https://www.pmcares.gov.in/en/")
        pm_cares.settings.javaScriptEnabled = true


        pm_cares.settings.setSupportZoom(true)

    }
}