package com.letstrackcovid.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_in_page.*
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class Sign_in_page : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    var callbackManager = CallbackManager.Factory.create();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)

        firebaseAuth = getInstance()

        FirebaseApp.initializeApp(this)

        fb_login_button.setPermissions("email")
        fb_login_button.setOnClickListener{
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            fbsignIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1042698241127-v92fjk8s3la69g48rjpnn2qgtp51sh4u.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()


        sign_in_button.setOnClickListener {
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }

    }

    private fun fbsignIn() {
        fb_login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }

        })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {

        val credential  = FacebookAuthProvider.getCredential(accessToken.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnFailureListener{ e->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

            }
            .addOnSuccessListener { result->

                val fbName = result.user!!.displayName

                val fbEmail = result.user!!.email

                var fbProfilePicURL = result.user!!.photoUrl.toString()

                var phonenumber = ""

                var bloodgroup = ""

                var age = ""

                var weight = ""

                var state = ""

                var city = ""

                val currentUserUid = firebaseAuth.currentUser!!.uid

                val ref  = FirebaseDatabase.getInstance("https://let-strackcovid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child("Facebook").child(currentUserUid)

                val fbUpload = fb_upload(fbName.toString(), fbEmail.toString(), fbProfilePicURL.toString(),phonenumber,bloodgroup,age,weight,state,city)

                try {
                    ref.setValue(fbUpload).addOnCompleteListener {

                        Toast.makeText(this, "data saved successfully", Toast.LENGTH_LONG).show()

                    }
                }
                catch (e : Exception)
                {
                    Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()

                }

                val baos = ByteArrayOutputStream()

                val image = baos.toByteArray()

                val storageRef = FirebaseStorage.getInstance("gs://let-strackcovid.appspot.com/").reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")


                val upload = storageRef.putBytes(image)

                upload.addOnCompleteListener{ uploadTask->
                    if (uploadTask.isSuccessful)
                    {
                        storageRef.downloadUrl.addOnCompleteListener{ urlTask->
                            urlTask.result?.let {
                                fbProfilePicURL = it.toString()

                            }

                        }
                    }
                    else{
                        Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
                    }
                }



            }

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

            }


    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
         }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)

            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val googleFirstName = account?.givenName ?: ""

                val googleLastName = account?.familyName ?: ""

                val googleEmail = account?.email ?: ""

                var googleProfilePicURL = account?.photoUrl.toString()

                var phonenumber = ""

                var bloodgroup = ""

                var age = ""

                var weight = ""

                var state = ""

                var city = ""

                val currentUserUid = firebaseAuth.currentUser!!.uid

                val ref  = FirebaseDatabase.getInstance("https://let-strackcovid-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child("Google").child(currentUserUid)

                val dataUpload = Data_upload(googleFirstName,googleLastName,googleEmail,googleProfilePicURL,phonenumber,bloodgroup,age,weight,state,city)

                try {
                    ref.setValue(dataUpload).addOnCompleteListener {

                        Toast.makeText(this, "data saved successfully", Toast.LENGTH_LONG).show()

                    }
                }
                catch (e : Exception)
                    {
                        Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()

                    }

                    val baos = ByteArrayOutputStream()

                    val image = baos.toByteArray()

                    val storageRef = FirebaseStorage.getInstance("gs://let-strackcovid.appspot.com/").reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")


                    val upload = storageRef.putBytes(image)

                    upload.addOnCompleteListener{ uploadTask->
                        if (uploadTask.isSuccessful)
                        {
                            storageRef.downloadUrl.addOnCompleteListener{ urlTask->
                                urlTask.result?.let {
                                    googleProfilePicURL = it.toString()

                                }

                            }
                        }
                        else{
                             Toast.makeText(this,"ERROR",Toast.LENGTH_LONG).show()
                            }
                    }



                }

                 intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
                 finish()

        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, MainActivity::class.java
                )
            )
            finish()
        }
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null)
        {
            //Toast.makeText(this,"No such user!",Toast.LENGTH_LONG).show()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}