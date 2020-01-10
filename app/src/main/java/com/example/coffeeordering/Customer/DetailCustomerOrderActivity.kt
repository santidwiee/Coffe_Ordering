package com.example.coffeeordering.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_customer_order.*
import java.util.HashMap

class DetailCustomerOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_customer_order)

//        val User = FirebaseAuth.getInstance().currentUser!!.uid
//        val databaseUserRef = FirebaseDatabase.getInstance().getReference("")



        val totalPrice = intent.getStringExtra(Constant.KEY_TOTAL_CHART)

        btn_confirm_order.setOnClickListener {
            val name = namaCustomer.text.toString()
            val phone = telpCustomer.text.toString()
            val address = alamatCustomer.text.toString()

            val id_user = FirebaseAuth.getInstance().currentUser!!.uid

            val databaseOrder = FirebaseDatabase.getInstance().getReference("Order").child(id_user)
            val orderid = databaseOrder.key.toString()

            val hashMap = HashMap<String, String>()
            hashMap["user_id"] = id_user!!
            hashMap["orderid"] = orderid
            hashMap["name"] = name
            hashMap["telp"] = phone
            hashMap["alamat"] = address
            hashMap["total"] = totalPrice

            databaseOrder.child(orderid).setValue(hashMap).addOnCompleteListener {

                val databaseRef = FirebaseDatabase.getInstance().getReference("CartCustomer").child(id_user)
                databaseRef.removeValue()

                Toast.makeText(this, "Order has submit", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))

            }
        }

    }
}
