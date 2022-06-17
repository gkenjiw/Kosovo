package com.example.kosovo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kosovo.databinding.ActivityMainBinding
import com.example.kosovo.databinding.CardBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.component1

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database: DatabaseReference

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
        } else {
            setupFirebase()
            listProducts()
        }

        binding.tab.setOnClickListener() {
        }
    }


    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Autenticado", Toast.LENGTH_LONG).show()
            setupFirebase()
            listProducts()
        } else {
            Toast.makeText(this, "Ka otario nao tem conta", Toast.LENGTH_LONG).show()
            finishAffinity()
        }
    }

    fun setupFirebase() {
        val user = getCurrentUser()

        if (user != null) {
            database = FirebaseDatabase.getInstance().reference.child(user.uid)
        }
    }

    fun listProducts() {
        binding.container.removeAllViews()

        val storage = FirebaseStorage.getInstance()
        val listRef = storage.reference.child("Products")

        listRef.listAll()
            .addOnSuccessListener { (items) ->
                items.forEach { item ->
                    val cardBinding = CardBinding.inflate(layoutInflater)

                    cardBinding.name.text = item.name
                    Glide.with(this)
                        .load(item)
                        .into(cardBinding.image)

                    binding.container.addView(cardBinding.root)


                }
            }
    }
}