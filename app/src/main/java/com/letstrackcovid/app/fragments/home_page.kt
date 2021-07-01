package com.letstrackcovid.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.covidapp.allaboutcovid.adapters.Recent_cases_adapter
import com.covidapp.allaboutcovid.adapters.features_adapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.letstrackcovid.app.R
import com.letstrackcovid.app.Recent_cases_data
import com.letstrackcovid.app.features_data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home_page.*

class home_page : Fragment(R.layout.fragment_home_page) {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_page, container, false)


        firebaseAuth = FirebaseAuth.getInstance()


        FirebaseApp.initializeApp(requireActivity())

        val currentUserUid = firebaseAuth.currentUser!!.uid

        val name = view.findViewById(R.id.Username) as TextView
        val image = view.findViewById(R.id.User_img) as ImageView

        val google_ref = FirebaseDatabase.getInstance("https://let-strackcovid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child("Google").child(currentUserUid)

        val fb_ref = FirebaseDatabase.getInstance("https://let-strackcovid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child("Facebook").child(currentUserUid)

        for (user in firebaseAuth.currentUser!!.providerData) {
            if (user.providerId == "facebook.com") {

                fb_ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            var fb_name = snapshot.child("fb_name").getValue()
                            var fb_prof_img = snapshot.child("fb_profile_img").getValue().toString()


                            Picasso.with(activity).load(fb_prof_img).into(image)

                            name.text = "$fb_name"

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }

            else if (user.providerId == "google.com") {

                google_ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            var gfname = snapshot.child("google_first_name").getValue()
                            var glname = snapshot.child("google_last_name").getValue()
                            var gprof_img = snapshot.child("profile_img").getValue().toString()


                            Picasso.with(activity).load(gprof_img).into(image)

                            name.text = "Hello, $gfname $glname!"

                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }

                return view

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)


        featureview.apply {

            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

            val fcases = ArrayList<features_data>()

            fcases.add(features_data(R.drawable.how_to_save_google_maps_route_offline5_1584447899, "Maps"))
            fcases.add(features_data(R.drawable.largest_vaccine_banner, "Cowin Website"))
            fcases.add(features_data(R.drawable.pmcares, "PM CARES fund Website"))
            fcases.add(features_data( R.drawable.aarogya_setu,"Aarogya Setu app link"))



            val adapter = features_adapter(fcases)

            featureview.adapter = adapter

        }

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