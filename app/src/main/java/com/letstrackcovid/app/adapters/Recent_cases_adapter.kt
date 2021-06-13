package com.covidapp.allaboutcovid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.letstrackcovid.app.R
import com.letstrackcovid.app.Recent_cases_data
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class Recent_cases_adapter(val recent_casesitems : ArrayList<Recent_cases_data>) : RecyclerView.Adapter<Recent_cases_adapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val place  = itemView.findViewById(R.id.place) as TextView
        val active  = itemView.findViewById(R.id.active) as TextView
        val anum  = itemView.findViewById(R.id.anum) as TextView
        val recovered  = itemView.findViewById(R.id.recovered) as TextView
        val rnum  = itemView.findViewById(R.id.rnum) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Recent_cases_adapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.cases_stats,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(parent :ViewHolder, position: Int) {

        val rcdata : Recent_cases_data = recent_casesitems[position]
        parent.place.text = rcdata.place
        parent.active.text = rcdata.active
        parent.anum.text = rcdata.anum.toString()
        parent.recovered.text = rcdata.recovered
        parent.rnum.text = rcdata.rnum.toString()

    }

    override fun getItemCount(): Int {
        return recent_casesitems.size
    }

}


