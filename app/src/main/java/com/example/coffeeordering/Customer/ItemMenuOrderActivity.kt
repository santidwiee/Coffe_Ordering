package com.example.coffeeordering.Customer

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_menu_order.*
import java.util.HashMap

class ItemMenuOrderActivity : AppCompatActivity(){

    private var item = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_menu_order)

        val id = intent.getStringExtra(Constant.KEY_ID_MENU)
        val name = intent.getStringExtra(Constant.KEY_NAME)
        val price = intent.getStringExtra(Constant.KEY_PRICE)
        val image = intent.getStringExtra(Constant.KEY_IMAGE)

        Glide.with(this)
            .asBitmap()
            .load(image)
            .into(img_menuOrder)

        checkMenuOrder.text = name
        hargaMenuOrder.text = price
        count.text = "1"
        total.text = price


        val id_user = FirebaseAuth.getInstance().currentUser?.uid
        val databaseRefAdmin = FirebaseDatabase.getInstance().getReference("CartAdmin").child(id_user!!).child(id)
        val databaseRefCustomer = FirebaseDatabase.getInstance().getReference("CartCustomer").child(id_user!!).child(id)


        //button command
        increase.setOnClickListener {
            val Price = price.toInt()
            increasing(Price)
        }

        decrease.setOnClickListener {
            val Price = price.toInt()
            decreasing(Price)
        }


        addCart.setOnClickListener {
            if(count.text.toString()=="0"){
                Toast.makeText(this, "Please press your quantity product", Toast.LENGTH_SHORT).show()
            }
            else {
                val itemBarang = count.text.toString()
                val totalHarga = total.text.toString()
//                val id_user = FirebaseAuth.getInstance().currentUser!!.uid

//                val db_tableChart = FirebaseDatabase.getInstance().getReference("Chart").push()
//                val db_tableChartAdmin = FirebaseDatabase.getInstance().getReference("ChartAdmin").push()

                val hashMap = HashMap<String, String>()
                hashMap.put("user_id", id_user)
                hashMap.put("menuid", id)
                hashMap.put("name", name)
                hashMap.put("quantity", itemBarang)
                hashMap.put("total", totalHarga)

                databaseRefAdmin.setValue(hashMap).addOnCompleteListener {

                }

                databaseRefCustomer.setValue(hashMap).addOnCompleteListener {
                    Toast.makeText(this, "Products have entired in your cart", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }








    private fun increasing(Price: Int){
        item++
        count.text = Integer.toString(item)

        total.text = Integer.toString(TotalProduct(Price))
    }

    private fun decreasing(Price : Int){
        if (item > 0) {
            item--
        }
        count.text = Integer.toString(item)
        total.text = Integer.toString(TotalProduct(Price))
    }

    private fun TotalProduct(Price : Int): Int {
        return item * Price
    }
}