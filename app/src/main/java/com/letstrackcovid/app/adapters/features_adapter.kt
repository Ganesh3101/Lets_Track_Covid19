package com.covidapp.allaboutcovid.adapters

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letstrackcovid.app.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class features_adapter(val features_items : ArrayList<features_data>) : RecyclerView.Adapter<features_adapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val img  = itemView.findViewById(R.id.feature_image) as ImageView
        val text  = itemView.findViewById(R.id.feature_text) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): features_adapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.featurestats,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder :ViewHolder, position: Int) {

        val features_data : features_data = features_items[position]
        holder.text.text = features_data.features_text
        holder.img.setImageResource(features_data.images_features)

        holder.itemView.setOnClickListener{

            if(position == 0 )
            {
                val intent = Intent(holder.itemView.context, CovidTrackerMapsActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
            else if(position == 1)
            {
                val intent = Intent(holder.itemView.context, Cowin_web::class.java)
                holder.itemView.context.startActivity(intent)
            }
            else if(position == 2)
            {
                val intent = Intent(holder.itemView.context, PMCARES::class.java)
                holder.itemView.context.startActivity(intent)
            }
            else if(position == 3)
            {
                try {
                    val intent = Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=nic.goi.aarogyasetu"))
                    holder.itemView.context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val intent = Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=nic.goi.aarogyasetu"))
                    holder.itemView.context.startActivity(intent)
                }
            }



        }

    }

    override fun getItemCount(): Int {
        return features_items.size
    }

}


