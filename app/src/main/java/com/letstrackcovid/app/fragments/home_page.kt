package com.letstrackcovid.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.covidapp.allaboutcovid.adapters.Recent_cases_adapter
import com.letstrackcovid.app.CovidTrackerMapsActivity
import com.letstrackcovid.app.R
import com.letstrackcovid.app.Recent_cases_data
import kotlinx.android.synthetic.main.fragment_home_page.*

class home_page : Fragment(R.layout.fragment_home_page) {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_page, container, false)


        val Usernametext = view.findViewById(R.id.Username) as TextView
        Usernametext.text = "Hello, Ganesh!"

        val map_img = view.findViewById(R.id.mapbtn) as ImageButton
        map_img.setOnClickListener {
            val intent = Intent(activity, CovidTrackerMapsActivity::class.java)
            startActivity(intent)
        }

        val map__text = view.findViewById(R.id.textmapbtn) as TextView
        map__text.setOnClickListener {
            val intent = Intent(activity, CovidTrackerMapsActivity::class.java)
            startActivity(intent)
        }

        return view

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)



        dailyupdateview.apply {

            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

            val cases = ArrayList<Recent_cases_data>()

            cases.add(Recent_cases_data("Active Cases", "Recovered Cases", "Mumbai", 10000000, 100000))
            cases.add(Recent_cases_data("Active Cases", "Recovered Cases", "Maharashtra", 1000000, 100000000))
            cases.add(Recent_cases_data("Active Cases", "Recovered Cases", "India", 1000000000, 100000))
            cases.add(Recent_cases_data("Active Cases", "Recovered Cases", "World", 100000000, 10000000000000))
            

            val adapter = Recent_cases_adapter(cases)

            dailyupdateview.adapter = adapter

        }

 
    }
}