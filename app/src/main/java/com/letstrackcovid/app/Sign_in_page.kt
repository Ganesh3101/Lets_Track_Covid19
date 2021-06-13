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
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in_page.*


@Suppress("DEPRECATION")
class Sign_in_page : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    var callbackManager = CallbackManager.Factory.create();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)



        firebaseAuth = getInstance()
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("Users")



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
                val email = result.user!!.email
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "You logged with : " + email, Toast.LENGTH_LONG).show()
            }
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

//                if (googleFirstName.isNotEmpty() && googleLastName.isNotEmpty() && googleEmail.isNotEmpty())
//                {
//                    var model = Data_upload(googleFirstName,googleLastName,googleEmail)
//                    var UserUid = ref.push().key
//
//                    ref.child(UserUid!!).setValue(model)
//                }
//                else
//                {
//                    Toast.makeText(this,"User input is null",Toast.LENGTH_LONG).show()
//                }



//                database = FirebaseDatabase.getInstance().getReference("Users")
//
//                database.child("First name").setValue(googleFirstName)
//                database.child("Last name").setValue(googleLastName)
//
////                database.child("Users").child(googleEmail).setValue(googleFirstName)
//                database.child("Users").child(googleEmail).setValue(googleLastName)


                 intent = Intent(this, MainActivity::class.java)
//                 intent.putExtra("Google First Name", googleFirstName)
//                 intent.putExtra("Google Last Name", googleLastName)
                 startActivity(intent)
                 finish()
            }
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