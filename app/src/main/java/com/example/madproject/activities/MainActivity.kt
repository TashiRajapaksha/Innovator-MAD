package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.R
import com.example.madproject.models.ProductModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var productNameEditText: EditText
    private lateinit var productCategoryEditText: EditText
    private lateinit var innovatorNameEditText: EditText
    private lateinit var additionalDescriptionEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var productImageView: ImageView
    private lateinit var btnInsertData:Button


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDatabase.getInstance().reference

        btnInsertData = findViewById(R.id.sub)


        btnInsertData.setOnClickListener{
            val intent=Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        productNameEditText = findViewById(R.id.ipname)
        productCategoryEditText = findViewById(R.id.ipcat)
        innovatorNameEditText = findViewById(R.id.iiname)
        additionalDescriptionEditText = findViewById(R.id.iad)
        productPriceEditText = findViewById(R.id.ipprice)
        productImageView = findViewById(R.id.ui)

        var dbRef = FirebaseDatabase.getInstance().getReference("Product")

        val submitButton: Button = findViewById(R.id.sub)
        submitButton.setOnClickListener {
            submitForm()
        }

        val uploadImageButton: Button = findViewById(R.id.ui)
        submitButton.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val productName = productNameEditText.text.toString()
        val productCategory = productCategoryEditText.text.toString()
        val innovatorName = innovatorNameEditText.text.toString()
        val additionalDescription = additionalDescriptionEditText.text.toString()
        val productPrice = productPriceEditText.text.toString()

        if(productName.isEmpty()){
            productNameEditText.error = "Please Enter name"
        }
        if(productCategory.isEmpty()){
            productCategoryEditText.error = "Please Enter product category"
        }
        if(innovatorName.isEmpty()){
            innovatorNameEditText.error = "Please Enter the innovator's name"
        }
        if(additionalDescription.isEmpty()){
            additionalDescriptionEditText.error = "Please Enter additional description"
        }
        if(productPrice.isEmpty()){
            productPriceEditText.error = "Please Enter the price"
        }

        val dbRef = FirebaseDatabase.getInstance().getReference("Product")
        val proId = dbRef.push().key!!

        val product = ProductModel(proId, productName, productCategory, innovatorName, additionalDescription, productPrice)

        dbRef.child(proId).setValue(product)

            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                    productNameEditText.text.clear()
                    productCategoryEditText.text.clear()
                    innovatorNameEditText.text.clear()
                    additionalDescriptionEditText.text.clear()
                    productPriceEditText.text.clear()

                } else {
                    Toast.makeText(this, "Error ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }



        // Do something with the form data, such as submitting it to a server or storing it locally
        val activity2 = MainActivity2()

        // Use a bundle to pass data to the success message fragment
        val bundle = Bundle()
        bundle.putString("productName", productName)
        bundle.putString("productCategory", productCategory)
        bundle.putString("innovatorName", innovatorName)
        bundle.putString("additionalDescription", additionalDescription)
        bundle.putString("productPrice", productPrice)
        //activity2.arguments = bundle

        /*supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragmentSuccess)
            .addToBackStack(null)
            .commit()*/
    }
}