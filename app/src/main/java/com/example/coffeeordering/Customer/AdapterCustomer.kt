package com.example.coffeeordering.Customer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeordering.Admin.Menu
import com.example.coffeeordering.R

class AdapterCustomer (val menuList: List<Menu>, val context: Context) : RecyclerView.Adapter<AdapterCustomer.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_customer, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = menuList[position].name
        holder.price.text = menuList[position].price
        Glide.with(context).load(menuList[position].image).into(holder.image)

        holder.buy.setOnClickListener()
        {
            val id = menuList[position].id
            val nama_product = menuList[position].name
            val harga_product = menuList[position].price
            val image_product = menuList[position].image

            val intent = Intent(context, ItemMenuOrderActivity::class.java)
            intent.putExtra(Constant.KEY_ID_MENU, id)
            intent.putExtra(Constant.KEY_NAME, nama_product)
            intent.putExtra(Constant.KEY_PRICE, harga_product)
            intent.putExtra(Constant.KEY_IMAGE, image_product)
            context.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }

    }


    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.checkMenuCustomer) as TextView
        val price = itemView.findViewById(R.id.hargaMenuCustomer) as TextView
        val image = itemView.findViewById(R.id.img_menuCustomer) as ImageView

        val buy = itemView.findViewById(R.id.TextBuy) as Button
    }
}