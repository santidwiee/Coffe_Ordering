package com.example.coffeeordering.Admin

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeordering.R
import com.google.firebase.database.FirebaseDatabase

class Adapter(val menuList: ArrayList<Menu>, val context: Context) : RecyclerView.Adapter<Adapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int
    {
        return menuList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int)
    {
        holder.name.text = menuList[position].name
        holder.price.text = menuList[position].price
        Glide.with(context).load(menuList[position].image).into(holder.image)

        // if user click on update icon for  update operation
        holder.edit.setOnClickListener()
        {
            val perItemPosition = menuList[position]
            updateDialog(perItemPosition)
        }

        // if user click on delete icon for delete operation
        holder.delete.setOnClickListener()
        {
            val perItemPosition = menuList[position]
            deletedata(perItemPosition.id)
        }

    }


    // holder class
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById(R.id.checkMenu) as CheckBox
        val price = itemView.findViewById(R.id.hargaMenu) as TextView
        val image = itemView.findViewById(R.id.img_menu) as ImageView

        // action operation widget
        val edit = itemView.findViewById(R.id.TextUpdate) as TextView
        val delete = itemView.findViewById(R.id.TextDelete) as TextView

    }

    // update dialog show method
    private fun updateDialog(perItemPosition: Menu) {

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.activity_update_menu, null)
        builder.setCancelable(false)

        val editext1 = view.findViewById<EditText>(R.id.name_menu)
        val editext2 = view.findViewById<EditText>(R.id.price_menu)

        // set exist data from recycler to dialog field
        editext1.setText(perItemPosition.name)
        editext2.setText(perItemPosition.price)

        // now set view to builder
        builder.setView(view)
        // now set positive negative button in alertdialog
        builder.setPositiveButton("Update", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                // update operation below
                val databaseref = FirebaseDatabase.getInstance().getReference("Menu")

                val name = editext1.text.toString()
                val price = editext2.text.toString()
                val image = perItemPosition.image

                if (name.isEmpty() && price.isEmpty())
                {
                    editext1.error = "please Fill up data"
                    editext1.requestFocus()
                    return
                }
                else
                {
                    // update data
                    val std_data = Menu(perItemPosition.id,name,price,image)
                    databaseref.child("${perItemPosition.id}").setValue(std_data)
                    Toast.makeText(context, "Data Updated", Toast.LENGTH_SHORT).show()

                }


            }
        })

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {


            }
        })
        // show dialog now
        val alert = builder.create()
        alert.show()
    }

    // delete operation
    private fun deletedata(menuid: String)
    {
        val databaseref = FirebaseDatabase.getInstance().getReference("Menu").child(menuid)
        databaseref.removeValue().addOnCompleteListener()
        {
            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
    }

//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Update Menu")
//
//            val inflater = LayoutInflater.from(context)
//            val view = inflater.inflate(R.layout.activity_update_menu, null)
//
//            //inisialisasi atribut
//            val textName = view.findViewById(R.id.name_menu) as EditText
//            val textPrice = view.findViewById(R.id.price_menu) as EditText
//
//            textName.setText(menu.name)
//            textPrice.setText(menu.price)
//
//            builder.setView(view)
//
//            builder.setPositiveButton("Update"){ dialog, which ->
//
//                val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")
//                val name = textName.text.toString()
//                val image = menu.image
//                val price = textPrice.text.toString()
//
//                if (name.isEmpty()){
//                    textName.error = "please enter name"
//                    textName.requestFocus()
//                    return@setPositiveButton
//                }
//
//                if (price.isEmpty()){
//                    textPrice.error = "please enter price"
//                    textPrice.requestFocus()
//                    return@setPositiveButton
//                }
//
//                else{
//                    val menu_data = Menu(menu.id,name,price,image)
//                    menu.id?.let {
//                        databaseRef.child(it).setValue(menu_data) }
//                    Toast.makeText(context,"Updated", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            builder.setNegativeButton("No") { dialog, which ->
//
//            }
//
//            val alert = builder.create()
//            alert.show()
    //}



    }


