package com.example.kosovo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kosovo.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getCurrentUser() == null) {
            var providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build(),
                                        AuthUI.IdpConfig.AnonymousBuilder().build())

            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
                1
            )


        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Autenticado caralhooo", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Ka otario nao tem conta", Toast.LENGTH_LONG).show()
            finishAffinity()
        }
    }
}