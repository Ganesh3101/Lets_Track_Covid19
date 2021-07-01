package com.letstrackcovid.app

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.covidapp.allaboutcovid.adapters.Covid_news_adapter
import com.mashape.unirest.http.Unirest
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import kotlinx.coroutines.GlobalScope as GlobalScope1

class NewsActivity : AppCompatActivity()
    //,Covid_news_adapter.NewsItemClicked
{
    private var progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        Cvnews_recycler.layoutManager = LinearLayoutManager(this)
        getData()
        progressBar = findViewById(R.id.progressBar) as ProgressBar


    }

    private fun getData() {


        GlobalScope1.launch(Dispatchers.Default) {  // replaces doAsync
            var response = Unirest.get("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/news/get-coronavirus-news/0")
                .header("x-rapidapi-key", "8f363d2899msh2c7fabcc88d13fep1424a3jsne5a9bcc238fe")
                .header("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com")
                .asString()

            var all = response.body

            val jsonObject = JSONTokener(all).nextValue() as JSONObject

            val jsonArray = jsonObject.getJSONArray("news")

            try {

                launch(Dispatchers.Main) { // replaces uiThread

                    val newsArray = ArrayList<news_data>()
                    for (i in 0 until jsonArray.length()) {

                        val newsObject = jsonArray.getJSONObject(i)
                        val news = news_data(
                            newsObject.getString("title"),
                            newsObject.getString("reference"),
                            newsObject.getString("urlToImage"),
                            newsObject.getString("link")

                        )
                        Thread(Runnable {
                            this@NewsActivity.runOnUiThread(java.lang.Runnable {
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
                            this@NewsActivity.runOnUiThread(java.lang.Runnable {
                                progressBar!!.visibility = View.GONE
                                val adapter = Covid_news_adapter(newsArray)

                                Cvnews_recycler.adapter = adapter

                                newsArray.add(news)


                            })
                        }).start()

                    }


                }
            }
            catch (e : Exception) {

                Toast.makeText(this@NewsActivity,"Sorry Network TimeOut!...Kindly Restart App",Toast.LENGTH_LONG).show()


            }


        }

    }


//    override fun onItemClicked(item: news_data) {
//
//        val builder =  CustomTabsIntent.Builder()
//        val customTabsIntent = builder.build()
//        customTabsIntent.launchUrl(this, Uri.parse(item.URL))
//    }


}



