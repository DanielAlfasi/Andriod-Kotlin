package com.example.myapplication

import Item
import ShoppingCart
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var shoppingCart = ShoppingCart()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shoppingCart = intent.getParcelableExtra<ShoppingCart>("shopping_cart")!!
        val dialogForOldNavyShirt = findViewById<ImageView>(R.id.image_old_navy_shirt)
        val dialogForBlueShirt = findViewById<ImageView>(R.id.image_blue_shirt)
        val dialogForGrayShirt = findViewById<ImageView>(R.id.image_gray_shirt)
        val dialogForBlueJeans = findViewById<ImageView>(R.id.image_blue_jeans)
        val dialogForBlackPants = findViewById<ImageView>(R.id.image_black_pants)
        val dialogForGrayPants = findViewById<ImageView>(R.id.image_gray_pants)
        val dialogForBackpack = findViewById<ImageView>(R.id.image_backpack)
        val dialogForSunglasses = findViewById<ImageView>(R.id.image_sunglasses)
        val dialogForWhiteHat = findViewById<ImageView>(R.id.image_white_hat)
        val goToCartButton = findViewById<ImageView>(R.id.cart_button)


        // Set an OnClickListener on the button
        goToCartButton.setOnClickListener {
            val goToShoppingCartActivity = Intent(this, ShoppingCartActivity::class.java)
            goToShoppingCartActivity.putExtra("shopping_cart", shoppingCart)
            startActivity(goToShoppingCartActivity)
        }


        dialogForOldNavyShirt.setOnClickListener {
            displayImageDialog("old_navy_shirt", getString(R.string.main_activity_old_navy_shirt), 20.0)
        }
        dialogForBlueShirt.setOnClickListener {
            displayImageDialog("blue_shirt", getString(R.string.main_activity_blue_shirt), 19.0)
        }
        dialogForGrayShirt.setOnClickListener {
            displayImageDialog("gray_shirt", getString(R.string.main_activity_gray_shirt), 20.0)
        }
        dialogForBlueJeans.setOnClickListener {
            displayImageDialog("blue_jeans", getString(R.string.main_activity_blue_jeans), 30.0)
        }
        dialogForBlackPants.setOnClickListener {
            displayImageDialog("black_pants", getString(R.string.main_activity_black_pants), 35.0)
        }
        dialogForGrayPants.setOnClickListener {
            displayImageDialog("gray_pants", getString(R.string.main_activity_gray_pants), 25.0)
        }
        dialogForBackpack.setOnClickListener {
            displayImageDialog("backpack", getString(R.string.main_activity_backpack), 15.99)
        }
        dialogForSunglasses.setOnClickListener {
            displayImageDialog("sunglasses", getString(R.string.main_activity_sunglasses), 10.99)
        }
        dialogForWhiteHat.setOnClickListener {
            displayImageDialog("white_hat", getString(R.string.main_activity_hat), 9.99)
        }

    }


    private fun displayImageDialog(imagePath: String, name: String, price: Double){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.my_dialog_layout)

        val item = Item(name, imagePath, price, 1)

        // Check if the item already exists in the shopping cart
        val existingItem = shoppingCart.getItems().find { it.name == name }
        val dialogDeclineButton = dialog.findViewById<Button>(R.id.dialog_decline_button)
        val dialogAcceptButton = dialog.findViewById<Button>(R.id.dialog_accept_button)

        if (existingItem != null) {
            dialogDeclineButton.text = getString(R.string.activity_purchase_remove_button)
            dialogAcceptButton.text = getString(R.string.activity_purchase_update_button)
            item.quantity = existingItem.quantity
            shoppingCart.removeItem(existingItem)
        }


        // Set the dialog contents - image, name, and price
        val dialogImageView = dialog.findViewById<ImageView>(R.id.dialog_image)
        val resourceId = resources.getIdentifier(item.imagePath, "drawable", packageName)
        dialogImageView.setImageResource(resourceId)

        val dialogNameTextView = dialog.findViewById<TextView>(R.id.dialog_name)
        dialogNameTextView.text = item.name

        val dialogPriceTextView = dialog.findViewById<TextView>(R.id.dialog_price)
        dialogPriceTextView.text = (item.price * item.quantity).toString() + "$"


        val dialogQuantityOfCurrentItemView = dialog.findViewById<TextView>(R.id.dialog_quantity_amount)
        dialogQuantityOfCurrentItemView.text = item.quantity.toString()


        val dialogSubtractButtonView = dialog.findViewById<Button>(R.id.dialog_quantity_subtract)
        if (item.quantity > 1)
        {
            dialogSubtractButtonView.isEnabled = true
        }

        val dialogAddButtonView = dialog.findViewById<Button>(R.id.dialog_quantity_add)
        dialogAddButtonView.setOnClickListener{
            item.quantity++
            dialogSubtractButtonView.isEnabled = true
            dialogQuantityOfCurrentItemView.text = item.quantity.toString()
            dialogPriceTextView.text = (item.price * item.quantity).toString() + "$"
        }

        dialogSubtractButtonView.setOnClickListener{
            item.quantity--
            if (item.quantity == 1)
            {
                dialogSubtractButtonView.isEnabled = false
            }
            dialogPriceTextView.text = (item.price * item.quantity).toString() + "$"
            dialogQuantityOfCurrentItemView.text = item.quantity.toString()
        }

        // Set up the accept and decline buttons
        dialogAcceptButton.setOnClickListener {
            // Add the item to the shopping cart
            shoppingCart.addItem(item)
            // Handle accept button click
            dialog.dismiss()
        }


        dialogDeclineButton.setOnClickListener {
            // Handle decline button click
            dialog.dismiss()
        }
        dialog.show()
    }

}

