import android.os.Parcel
import android.os.Parcelable

class Item(val name: String, val imagePath: String, var price: Double, var quantity: Int = 1) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imagePath)
        parcel.writeDouble(price)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

class ShoppingCart() : Parcelable {
    private val items = mutableListOf<Item>()

    constructor(parcel: Parcel) : this() {
        parcel.readList(items, Item::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShoppingCart> {
        override fun createFromParcel(parcel: Parcel): ShoppingCart {
            return ShoppingCart(parcel)
        }

        override fun newArray(size: Int): Array<ShoppingCart?> {
            return arrayOfNulls(size)
        }
    }

    fun addItem(item: Item) {
        val existingItem = items.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            items.add(item)
        }
    }

    fun removeItem(item: Item) {
        items.remove(item)
    }

    fun getItems(): List<Item> {
        return items.toList()
    }



    fun getTotalPrice(): Double {
        var total = 0.0
        for (item in items) {
            total += item.price * item.quantity
        }
        return total
    }
}

