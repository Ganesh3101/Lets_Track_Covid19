package com.letstrackcovid.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Profile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    var phonenumber = "1"
    val bloodgrpList = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
    val agelist = arrayOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
            "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
            "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
            "91", "92", "93", "94", "95", "96", "97", "98", "99", "100")

    val weightlist = arrayOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
            "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
            "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
            "91", "92", "93", "94", "95", "96", "97", "98", "99", "100",
            "101", "102", "103", "104", "105", "106", "107", "108", "109", "110",
            "111", "112", "113", "114", "115", "116", "117", "118", "119", "120",
            "121", "122", "123", "124", "125", "126", "127", "128", "129", "130",
            "131", "132", "133", "134", "135", "136", "137", "138", "139", "140",
            "141", "142", "143", "144", "145", "146", "147", "148", "149", "150")

    val statelist = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    val citylist = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseAuth = FirebaseAuth.getInstance()


        FirebaseApp.initializeApp(this)

        val currentUserUid = firebaseAuth.currentUser!!.uid

        val prof_img      = findViewById(R.id.profile_img) as ImageView
        val textViewname = findViewById(R.id.Username) as TextView
        val textViewemail = findViewById(R.id.email_id) as TextView

        val age_text =findViewById(R.id.user_age_spinner_text) as TextView
        val age_spinner = findViewById(R.id.user_age_spinner) as Spinner

        val blood_grp_text = findViewById(R.id.user_blood_group_spinner_text) as TextView
        val blood_grp_spinner = findViewById(R.id.user_blood_group_spinner) as Spinner

        val weight_text = findViewById(R.id.user_weight_spinner_text) as TextView
        val weight_spinner =findViewById(R.id.user_weight_spinner) as Spinner

        var final_text_num = findViewById(R.id.finalnum_input) as EditText

        val state_text = findViewById(R.id.state_spinner_text) as TextView
        val state_spinner = findViewById(R.id.state_spinner) as Spinner

        val city_text = findViewById(R.id.city_spinner_text) as TextView
        val city_spinner = findViewById(R.id.city_spinner) as Spinner

        val update_btn = findViewById(R.id.update_firebase) as TextView

        blood_grp_spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bloodgrpList)

        blood_grp_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                blood_grp_text.text = bloodgrpList.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                blood_grp_text.text = ""
            }

        }

        weight_spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, weightlist)

        weight_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                weight_text.text = weightlist.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                weight_text.text = ""
            }

        }

        age_spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, agelist)

        age_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                age_text.text = agelist.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                age_text.text = ""
            }

        }

        state_spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, statelist)

        state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                state_text.text = statelist.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                state_text.text = ""
            }

        }

        city_spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, citylist)

        city_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city_text.text = citylist.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

                city_text.text = ""
            }

        }

        val google_ref = FirebaseDatabase.getInstance("https://let-strackcovid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child("Google").child(currentUserUid)

        val fb_ref = FirebaseDatabase.getInstance("https://let-strackcovid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child("Facebook").child(currentUserUid)

        for (user in firebaseAuth.currentUser!!.providerData) {
            if (user.providerId == "facebook.com") {

                fb_ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            var fb_name = snapshot.child("fb_name").getValue()
                            var fb_email = snapshot.child("fb_email").getValue()
                            var fb_prof_img = snapshot.child("fb_profile_img").getValue().toString()
                            var fb_phonenumber = snapshot.child("fb_mobile_number").getValue().toString()
                            var fb_bloodgrp = snapshot.child("fb_blood_group").getValue()
                            val fb_age = snapshot.child("fb_age").getValue()
                            val fb_weight = snapshot.child("fb_weight").getValue()
                            val fb_state = snapshot.child("fb_state").getValue()
                            val fb_city = snapshot.child("fb_city").getValue()

                            Picasso.with(applicationContext).load(fb_prof_img).into(prof_img)

                            textViewname.text = "$fb_name"
                            textViewemail.text = fb_email.toString()
                            final_text_num.setText(fb_phonenumber, TextView.BufferType.EDITABLE)
                            blood_grp_text.text = fb_bloodgrp.toString()
                            age_text.text = fb_age.toString()
                            weight_text.text = fb_weight.toString()
                            state_text.text = fb_state.toString()
                            city_text.text = fb_city.toString()
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
                            var gemail = snapshot.child("google_email").getValue()
                            var gprof_img = snapshot.child("profile_img").getValue().toString()
                            var phonenumber = snapshot.child("mobile_number").getValue().toString()
                            var bloodgrp = snapshot.child("blood_group").getValue()
                            val age = snapshot.child("age").getValue()
                            val weight = snapshot.child("weight").getValue()
                            val state = snapshot.child("state").getValue()
                            val city = snapshot.child("city").getValue()

                            Picasso.with(applicationContext).load(gprof_img).into(prof_img)

                            textViewname.text = "$gfname $glname"
                            textViewemail.text = gemail.toString()
                            final_text_num.setText(phonenumber, TextView.BufferType.EDITABLE)
                            blood_grp_text.text = bloodgrp.toString()
                            age_text.text = age.toString()
                            weight_text.text = weight.toString()
                            state_text.text = state.toString()
                            city_text.text = city.toString()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }


        update_btn.setOnClickListener {

            for (user in firebaseAuth.currentUser!!.providerData) {
                if (user.providerId == "facebook.com")
                {
                    phonenumber = final_text_num.text.toString()
                    var bloodgrp =  blood_grp_text.text.toString()
                    var age = age_text.text.toString()
                    var weight = weight_text.text.toString()
                    var state = state_text.text.toString()
                    var city = city_text.text.toString()


                    val maxlength = 10

                    if(phonenumber.length == maxlength)
                    {
                        fb_ref.child("fb_mobile_number").setValue(phonenumber)
                        fb_ref.child("fb_blood_group").setValue(bloodgrp)
                        fb_ref.child("fb_age").setValue(age)
                        fb_ref.child("fb_weight").setValue(weight)
                        fb_ref.child("fb_state").setValue(state)
                        fb_ref.child("fb_city").setValue(city)

                        Toast.makeText(this, "Data Updated Successsfully!", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this, "Check Entered Details Once Again!", Toast.LENGTH_LONG).show()

                    }


                }
                else if (user.providerId == "google.com")
                {
                    phonenumber = final_text_num.text.toString()
                    var bloodgrp =  blood_grp_text.text.toString()
                    var age = age_text.text.toString()
                    var weight = weight_text.text.toString()
                    var state = state_text.text.toString()
                    var city = city_text.text.toString()


                    val maxlength = 10

                    if(phonenumber.length == maxlength)
                    {
                        google_ref.child("mobile_number").setValue(phonenumber)
                        google_ref.child("blood_group").setValue(bloodgrp)
                        google_ref.child("age").setValue(age)
                        google_ref.child("weight").setValue(weight)
                        google_ref.child("state").setValue(state)
                        google_ref.child("city").setValue(city)
                        Toast.makeText(this, "Data Updated Successsfully!", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this, "Check Entered Details Once Again!", Toast.LENGTH_LONG).show()

                    }


                }
            }

        }

    }
}