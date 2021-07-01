package com.covidapp.allaboutcovid.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letstrackcovid.app.*
import java.util.*

class Covid_news_adapter(val newsitems: ArrayList<news_data>) : RecyclerView.Adapter<Covid_news_adapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.covid_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val newsdata :news_data  = newsitems[position]
        holder.title.text = newsdata.title
        holder.provider.text = newsdata.provider

        Glide.with(holder.itemView.context).load(newsdata.news_img).into(holder.news_img)

        holder.itemView.setOnClickListener{

            val intent = Intent(holder.itemView.context, Webview::class.java)
            intent.putExtra("CardClick", position)
            holder.itemView.context.startActivity(intent)

        }

    }


    override fun getItemCount(): Int {
        return newsitems.size

    }

//    fun updateNews(updatedNews: ArrayList<news_data>) {
//        items.clear()
//        items.addAll(updatedNews)
//
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title  = itemView.findViewById(R.id.Headlines) as TextView
        val provider  = itemView.findViewById(R.id.news_provider) as TextView
        val news_img  = itemView.findViewById(R.id.news_img) as ImageView


    }



//    interface NewsItemClicked {
//        fun onItemClicked(item: news_data)
//    }
//

}




