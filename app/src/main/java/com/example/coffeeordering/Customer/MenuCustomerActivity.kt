package com.example.coffeeordering.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeordering.Admin.Adapter
import com.example.coffeeordering.Admin.InsertMenuActivity
import com.example.coffeeordering.Admin.Menu
import com.example.coffeeordering.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_menu_admin.*
import kotlinx.android.synthetic.main.activity_menu_admin.rv_list_menu
import kotlinx.android.synthetic.main.activity_menu_customer.*

class MenuCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_customer)

        //button command
        CartCustomer.setOnClickListener {
            startActivity(Intent(this, CartCustomerActivity::class.java))
        }

        //init referensi
        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")


        val menu: ArrayList<Menu> = ArrayList()
        rv_list_menu_customer.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Info: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    menu.clear()
                    for(data in p0.children){
                        val getValue = data.getValue(Menu::class.java)
                        menu.add(getValue!!)
                    }
                }
                rv_list_menu_customer.adapter = AdapterCustomer( menu, this@MenuCustomerActivity)
            }
        })
    }
}
