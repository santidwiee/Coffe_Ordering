package com.example.coffeeordering.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeordering.Customer.AdapterCart
import com.example.coffeeordering.Customer.Cart
import com.example.coffeeordering.Customer.Constant
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart_customer.*
import kotlinx.android.synthetic.main.activity_detail_pesanan.*
import kotlinx.android.synthetic.main.activity_detail_pesanan.total_price
import kotlinx.android.synthetic.main.activity_detail_pesanan.total_quantity
import kotlinx.android.synthetic.main.activity_list_pesanan.*

class DetailPesananActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pesanan)

        //init referensi
        val mAuth = FirebaseAuth.getInstance().currentUser?.uid
        val databaseRef = FirebaseDatabase.getInstance().getReference("CartAdmin").child(mAuth!!)


        val cart: ArrayList<Cart> = ArrayList()
        rv_detail_order.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


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
                    for (data in p0.children) {
                        val tes = data.getValue(Cart::class.java)
                        cart.add(tes!!)

                        val t_quantity = data.getValue(Cart::class.java)?.quantity?.toString()
                        nilai_quantity += Integer.valueOf(t_quantity!!)


                        val t_price = data.getValue(Cart::class.java)?.total?.toString()
                        nilai_price += Integer.valueOf(t_price!!)
                    }


                    val adapter = AdapterCart(cart, this@DetailPesananActivity)
                    rv_order.adapter = adapter
                    adapter.notifyDataSetChanged()

                    total_quantity.text = nilai_quantity.toString()
                    total_price.text = nilai_price.toString()
                } else {
                    Toast.makeText(applicationContext, "Data not found", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}
