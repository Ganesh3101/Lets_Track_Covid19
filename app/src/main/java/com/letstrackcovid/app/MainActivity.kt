package com.letstrackcovid.app
//6a2d3d2de20f438e962fb9197366071d news api key
//972503126282-sv3aqkustdhtq49a605hnvauubroht95.apps.googleusercontent.com  OAuth - Client - Id
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.covidapp.allaboutcovid.adapters.Recent_cases_adapter
import com.letstrackcovid.app.fragments.help_fragment
import com.letstrackcovid.app.fragments.home_page
import com.letstrackcovid.app.fragments.news_fragment
import com.letstrackcovid.app.fragments.profile
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlin.collections.ArrayList


class MainActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profile_fragment = profile()
        val home_fragment = home_page()
        val news_fragment = news_fragment()
        val help_fragment = help_fragment()

        setCurrentFragment(home_fragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(home_fragment)
                R.id.news->setCurrentFragment(news_fragment)
                R.id.help->setCurrentFragment(help_fragment)
                R.id.profilepic->setCurrentFragment(profile_fragment)
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment)=
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,fragment)
                commit()
            }



//    private suspend fun getPredictions() {
//        try {
//            val result = GlobalScope.async {
//                callAztroAPI("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=" + sunSign + "&day=today")
//            }.await()
//
//            onResponse(result)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun callAztroAPI(apiUrl:String ):String?{
//        var result: String? = ""
//        val url: URL;
//        var connection: HttpURLConnection? = null
//        try {
//            url = URL(apiUrl)
//            connection = url.openConnection() as HttpURLConnection
//            // set headers for the request
//            // set host name
//            connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
//
//            // set the rapid-api key
//            connection.setRequestProperty("x-rapidapi-key", "<YOUR_RAPIDAPI_KEY>")
//            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded")
//            // set the request method - POST
//            connection.requestMethod = "POST"
//            val `in` = connection.inputStream
//            val reader = InputStreamReader(`in`)
//
//            // read the response data
//            var data = reader.read()
//            while (data != -1) {
//                val current = data.toChar()
//                result += current
//                data = reader.read()
//            }
//            return result
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        // if not able to retrieve data return null
//        return null
//
//    }

}
