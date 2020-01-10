package com.example.coffeeordering.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeordering.Admin.Menu
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart_customer.*
import kotlinx.android.synthetic.main.activity_menu_customer.*
import org.jetbrains.anko.startActivity
import kotlin.collections.mutableListOf as mutableListOf1

class CartCustomerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_customer)

        //init referensi
        val mAuth = FirebaseAuth.getInstance().currentUser?.uid
        val databaseRef = FirebaseDatabase.getInstance().getReference("CartCustomer").child(mAuth!!)


        val cart: ArrayList<Cart> = ArrayList()
        rv_order.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        //load data product
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Info: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                var nilai_quantity = 0
                var nilai_price = 0
                if (p0!!.exists()) {
                    cart.clear()
                    for(data in p0.children){
                        val tes = data.getValue(Cart::class.java)
                        cart.add(tes!!)

                        val t_quantity = data.getValue(Cart::class.java)?.quantity?.toString()
                        nilai_quantity += Integer.valueOf(t_quantity!!)


                        val t_price = data.getValue(Cart::class.java)?.total?.toString()
                        nilai_price += Integer.valueOf(t_price!!)
                    }


                    val adapter = AdapterCart(cart, this@CartCustomerActivity)
                    rv_order.adapter = adapter
                    adapter.notifyDataSetChanged()

                    total_quantity.text = nilai_quantity.toString()
                    total_price.text = nilai_price.toString()
                }
                else{
                    Toast.makeText(applicationContext, "Data not found", Toast.LENGTH_SHORT).show()
                }
            }
        })


        //button Command
        checkout.setOnClickListener {
            val totalCart = total_price .text.toString()

            val DetailCustomer = Intent(this, DetailCustomerOrderActivity::class.java)
            DetailCustomer.putExtra(Constant.KEY_TOTAL_CHART, totalCart)
            startActivity(DetailCustomer)
        }
    }
}

