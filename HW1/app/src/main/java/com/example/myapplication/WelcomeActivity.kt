package com.example.myapplication

import ShoppingCart
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class WelcomeActivity : AppCompatActivity() {
    private var shoppingCart = ShoppingCart()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcomepage)

        val goToMainActivityButton = findViewById<Button>(R.id.shop_now_button)
        goToMainActivityButton.setOnClickListener {
            val goToMainActivity = Intent(this, MainActivity::class.java)
            goToMainActivity.putExtra("shopping_cart", shoppingCart)
            startActivity(goToMainActivity)
        }

    }

}