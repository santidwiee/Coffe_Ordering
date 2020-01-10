package com.example.coffeeordering.Customer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdapterCart (val cartList: List<Cart>, val context: Context) : RecyclerView.Adapter<AdapterCart.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val id_user = FirebaseAuth.getInstance().currentUser?.uid
        val databaseRef = FirebaseDatabase.getInstance().getReference("CartCustomer").child(id_user!!)

        holder.name.text = cartList[position].name
        holder.quantity.text = cartList[position].quantity
        holder.price.text = cartList[position].total

        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setPositiveButton("Delete"){Dialog, which ->

                databaseRef.child(cartList[position].id).removeValue().addOnCompleteListener {
                    Toast.makeText(context, "Menu Deleted", Toast.LENGTH_SHORT).show()
                }

            }
            val alert = builder.create()
           alert.show()
        }

    }


    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById(R.id.tv_name) as TextView
        val quantity = itemView.findViewById(R.id.tv_quantity) as TextView
        val price = itemView.findViewById(R.id.tv_price) as TextView
    }
}