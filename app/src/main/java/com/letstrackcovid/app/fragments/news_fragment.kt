package com.letstrackcovid.app.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.covidapp.allaboutcovid.adapters.Covid_news_adapter
import com.letstrackcovid.app.NewsActivity
import com.letstrackcovid.app.R
import com.letstrackcovid.app.news_data
import com.mashape.unirest.http.Unirest
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_news_fragment.*



class news_fragment : Fragment(R.layout.fragment_news_fragment)  {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_news_fragment, container, false)

        val textView = view.findViewById(R.id.Cvnews_text) as TextView

        textView.setOnClickListener {

            val intent = Intent(activity, NewsActivity::class.java)
            startActivity(intent)

        }

        return view
    }
}
