package com.example.kosovo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kosovo.databinding.ActivityProductDetailBinding
import com.squareup.picasso.Picasso

class ProductDetail : AppCompatActivity() {
    lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get().load(intent.getStringExtra("image")).resize(200,200).into(binding.imageProduct)
        binding.nameProduct.text = intent.getStringExtra("name")
        binding.descProduct.text = intent.getStringExtra("desc")
        binding.priceProduct.text = intent.getStringExtra("price")
    }
}