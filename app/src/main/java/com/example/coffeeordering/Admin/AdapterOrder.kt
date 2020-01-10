package com.example.coffeeordering.Admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeordering.Customer.Constant
import com.example.coffeeordering.R

class AdapterOrder(val orderList: List<Order>, val context: Context) : RecyclerView.Adapter<AdapterOrder.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesanan, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int
    {
        return orderList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.orderid.text = orderList[position].orderid
        holder.name.text = orderList[position].name
        holder.telp.text = orderList[position].telp
        holder.alamat.text = orderList[position].alamat
        holder.total.text = orderList[position].total

//        holder.lihatpesanan.setOnClickListener {
//            val user_id = orderList[position].user_id
//
//            val intent = Intent(context, DetailPesananActivity::class.java)
//            intent.putExtra(Constant.KEY_ID_USER, user_id)
//            context.startActivity(intent)
//        }
    }


    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderid = itemView.findViewById(R.id.orderId) as TextView
        val name = itemView.findViewById(R.id.Name) as TextView
        val telp = itemView.findViewById(R.id.Telp) as TextView
        val alamat = itemView.findViewById(R.id.Alamat) as TextView
        val total = itemView.findViewById(R.id.Total) as TextView

//        val lihatpesanan = itemView.findViewById(R.id.lihatpesanan) as Button
    }
}