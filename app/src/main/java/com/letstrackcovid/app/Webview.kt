package com.letstrackcovid.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.covidapp.allaboutcovid.adapters.Covid_news_adapter
import com.mashape.unirest.http.Unirest
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener

class
Webview : AppCompatActivity() {

    private var progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        progressBar = findViewById(R.id.webprogress) as ProgressBar

        val cardclick = intent.getIntExtra("CardClick",0)

        GlobalScope.launch(Dispatchers.Default) {  // replaces doAsync
            var response = Unirest.get("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/news/get-coronavirus-news/0")
                    .header("x-rapidapi-key", "8f363d2899msh2c7fabcc88d13fep1424a3jsne5a9bcc238fe")
                    .header("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
                    .asString()

            var all = response.body

            val jsonObject = JSONTokener(all).nextValue() as JSONObject

            val jsonArray = jsonObject.getJSONArray("news")

            try {

                launch(Dispatchers.Main) { // replaces uiThread

                        val URL = jsonArray.getJSONObject(cardclick).getString("link")
                       
                        Thread(Runnable {
                            this@Webview.runOnUiThread(java.lang.Runnable {
                                progressBar!!.visibility = View.VISIBLE
                            })
                            try {
                                var i=0;
                                while(i<100){
                                    i++
                                }
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            // when the task is completed, make progressBar gone
                            this@Webview.runOnUiThread(java.lang.Runnable {
                                progressBar!!.visibility = View.GONE

                                webview_cvoid.webViewClient = WebViewClient()

                                //webview_cvoid.load(URL)
                                webview_cvoid.loadUrl(URL)
                                webview_cvoid.settings.javaScriptEnabled = true

                                // if you want to enable zoom feature
                                webview_cvoid.settings.setSupportZoom(true)

                            })
                        }).start()

                    }



            }
            catch (e : Exception) {

                Toast.makeText(this@Webview,"Sorry Network TimeOut!...Kindly Restart App", Toast.LENGTH_LONG).show()


            }


        }



    }
}