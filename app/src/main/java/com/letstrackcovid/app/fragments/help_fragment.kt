package com.letstrackcovid.app.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.letstrackcovid.app.Profile
import com.letstrackcovid.app.R
import com.mashape.unirest.http.Unirest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener

class help_fragment : Fragment(R.layout.fragment_help_fragment) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_help_fragment, container, false)


        val prof = view.findViewById(R.id.profile) as TextView
        prof.text = "HI"

        prof.setOnClickListener{
            val intent = Intent(requireActivity(), Profile::class.java)
            startActivity(intent)
        }

        return view

    }


}