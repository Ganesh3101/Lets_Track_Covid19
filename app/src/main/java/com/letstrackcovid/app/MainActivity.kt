package com.letstrackcovid.app
//6a2d3d2de20f438e962fb9197366071d news api key
//972503126282-sv3aqkustdhtq49a605hnvauubroht95.apps.googleusercontent.com  OAuth - Client - Id
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.letstrackcovid.app.fragments.help_fragment
import com.letstrackcovid.app.fragments.home_page
import com.letstrackcovid.app.fragments.news_fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home_page.*


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val home_fragment = home_page()
        val news_fragment = news_fragment()
        val help_fragment = help_fragment()

        setCurrentFragment(home_fragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(home_fragment)
                R.id.news->setCurrentFragment(news_fragment)
                R.id.help->setCurrentFragment(help_fragment)

            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment)=
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,fragment)
                commit()
            }



}
