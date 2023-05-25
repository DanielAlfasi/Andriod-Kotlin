package com.example.myapplication

import ShoppingCart
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.animation.AnimationUtils

class ShoppingCartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppingcart)

        val shoppingCart = intent.getParcelableExtra<ShoppingCart>("shopping_cart")
        val parentLayout = findViewById<LinearLayout>(R.id.parent_layout)

        if (shoppingCart != null) {
            addLinearLayouts(shoppingCart, parentLayout)
            createLinearLayoutWithButtons(parentLayout, shoppingCart, shoppingCart.getTotalPrice())
        }
    }

    private fun addLinearLayouts(i_ShoppingCart: ShoppingCart, parentLayout: LinearLayout) {
        val itemsOfShoppingCart = i_ShoppingCart.getItems()
        for (i in itemsOfShoppingCart.indices) {
            val linearLayout = LinearLayout(parentLayout.context)
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.setPadding(16, 16, 16, 16)
            linearLayout.visibility = View.VISIBLE

            val imageView = ImageView(parentLayout.context)
            val resourceId = resources.getIdentifier(itemsOfShoppingCart[i].imagePath, "drawable", packageName)
            imageView.layoutParams = LinearLayout.LayoutParams(
                96.dpToPx(),
                57.dpToPx()
            )
            (imageView.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER_VERTICAL or Gravity.START
            imageView.setImageResource(resourceId)

            val innerLinearLayout = LinearLayout(parentLayout.context)
            innerLinearLayout.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2f
            )
            innerLinearLayout.orientation = LinearLayout.VERTICAL
            innerLinearLayout.gravity = Gravity.CENTER_VERTICAL
            innerLinearLayout.setPadding(16, 0, 16, 0)

            val itemName = TextView(parentLayout.context)
            itemName.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemName.text = itemsOfShoppingCart[i].name
            itemName.setTextColor(ContextCompat.getColor(parentLayout.context, android.R.color.black))
            itemName.textSize = 16f

            val itemPrice = TextView(parentLayout.context)
            itemPrice.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemPrice.text = getString(R.string.activity_shopping_cart_price) + (itemsOfShoppingCart[i].price * itemsOfShoppingCart[i].quantity) + "$"
            itemPrice.setTextColor(ContextCompat.getColor(parentLayout.context, android.R.color.darker_gray))
            itemPrice.textSize = 14f

            val itemQuantity = TextView(parentLayout.context)
            itemQuantity.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemQuantity.text = getString(R.string.dialog_of_item_quantity) + itemsOfShoppingCart[i].quantity.toString()
            itemQuantity.setTextColor(ContextCompat.getColor(parentLayout.context, android.R.color.darker_gray))
            itemQuantity.textSize = 14f

            innerLinearLayout.addView(itemName)
            innerLinearLayout.addView(itemQuantity)
            innerLinearLayout.addView(itemPrice)

            linearLayout.addView(imageView)
            linearLayout.addView(innerLinearLayout)

            parentLayout.addView(linearLayout)

        }

    }

    private fun createLinearLayoutWithButtons(parentLayout: LinearLayout, shoppingCart: ShoppingCart,amountToPay: Double = 0.0) {
        val linearLayout = LinearLayout(parentLayout.context)
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        linearLayout.setPadding(20.dpToPx(), 20.dpToPx(), 20.dpToPx(), 20.dpToPx())

        val amountToPayTextView = TextView(parentLayout.context)
        amountToPayTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        amountToPayTextView.text = getString(R.string.activity_shopping_cart_total) + amountToPay + "$"

        val purchaseButton = Button(parentLayout.context)
        purchaseButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        purchaseButton.text = getString(R.string.activity_shopping_cart_purchase_now_button)
        purchaseButton.setOnClickListener{
            displayImageDialog(amountToPay)
        }

        val goBackButton = Button(parentLayout.context)
        goBackButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        goBackButton.text = getString(R.string.dialog_of_item_go_back_button)
        goBackButton.setOnClickListener{
            val goToMainActivity = Intent(this, MainActivity::class.java)
            goToMainActivity.putExtra("shopping_cart", shoppingCart)
            startActivity(goToMainActivity)
        }

        linearLayout.addView(amountToPayTextView)
        linearLayout.addView(purchaseButton)
        linearLayout.addView(goBackButton)

        parentLayout.addView(linearLayout)
    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
    }

    private fun displayImageDialog(price: Double){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_purchase)

        val dialogPriceTextView = dialog.findViewById<TextView>(R.id.dialog_price)
        dialogPriceTextView.text = getString(R.string.activity_shopping_cart_amount_to_pay) + price.toString() + "$"

        // Set up the accept and decline buttons
        val dialogAcceptButton = dialog.findViewById<Button>(R.id.dialog_accept_button)
        dialogAcceptButton.setOnClickListener {
            dialog.dismiss()
            startThankYouAnimation()
        }

        val dialogDeclineButton = dialog.findViewById<Button>(R.id.dialog_decline_button)
        dialogDeclineButton.setOnClickListener {
            // Handle decline button click
            dialog.dismiss()
        }
        dialog.show()
    }



    private fun startThankYouAnimation() {
        setContentView(R.layout.activity_thankyouforpurchase)
        val thankYouTextView = findViewById<TextView>(R.id.thankYouTextView)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_and_out)
        val goBackButton = findViewById<Button>(R.id.go_back_button)
        goBackButton.setOnClickListener {
            val goToWelcomeActivity = Intent(this, WelcomeActivity::class.java)
            startActivity(goToWelcomeActivity)
        }
        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                thankYouTextView.startAnimation(slideAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Not needed in this case
            }
        })

        thankYouTextView.startAnimation(slideAnimation)
    }


}




