package com.example.coffeeordering.Admin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeordering.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_insert_menu.*
import java.io.IOException

class InsertMenuActivity : AppCompatActivity(), View.OnClickListener {

    private var ImagePath: Uri? = null
    lateinit var databaseRef: DatabaseReference



    override fun onClick(v: View?) {
        if (v == choose_file) {
            ChooseFile()
        } else if (v == done) {
            UploadFile()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_menu)


        //init referensi
        databaseRef = FirebaseDatabase.getInstance().getReference("Menu")

        //button command
        choose_file.setOnClickListener {
            ChooseFile()
        }

        done.setOnClickListener {
            UploadFile()
        }


    }

    private fun ChooseFile() {
        val intentImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentImg, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            ImagePath = data.data;
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, ImagePath)
                image!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun UploadFile() {
        if (ImagePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val storageRef = FirebaseStorage.getInstance().getReference("Menu")

            storageRef.putFile(ImagePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "File Uploaded", Toast.LENGTH_SHORT).show()
                    storageRef.downloadUrl.addOnSuccessListener {
                        val name = name_menu.text.toString()
                        val price = price_menu.text.toString()
                        val image = it.toString()
                        val Id = databaseRef.push().key.toString()
                        val menu = Menu(Id, name, price, image)

                        databaseRef.child(Id).setValue(menu).addOnCompleteListener {
                            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                            name_menu.setText("")
                            price_menu.setText("")
                        }
                        finish()
                    }
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded" + progress.toInt() + "%...")
                }
        }
    }
}