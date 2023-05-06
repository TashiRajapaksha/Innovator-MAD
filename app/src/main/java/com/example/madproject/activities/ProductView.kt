package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.madproject.R
import com.example.madproject.models.ProductModel
import com.google.firebase.database.FirebaseDatabase

class ProductView : AppCompatActivity() {

    private lateinit var tvproductId: TextView
    private lateinit var tvproductName: TextView
    private lateinit var tvproductCategory: TextView
    private lateinit var tvinnovatorName: TextView
    private lateinit var tvadditionalDescription: TextView
    private lateinit var tvproductPrice: TextView
    private lateinit var update: Button
    private lateinit var delete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)

        initView()
        setValuesToViews()

        update.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("proId").toString(),
                intent.getStringExtra("productName").toString(),
                intent.getStringExtra("productCategory").toString(),
                intent.getStringExtra("InnovatorName").toString(),
                intent.getStringExtra("additionalDescription").toString(),
                intent.getStringExtra("productPrice").toString()
            )}
        delete.setOnClickListener{
           deleteRecord(
               intent.getStringExtra("proId").toString()
           )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("product").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Product Data Deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,ProductView::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvproductId = findViewById(R.id.pidv)
        tvproductName = findViewById(R.id.pnamev)
        tvproductCategory = findViewById(R.id.pcatv)
        tvinnovatorName = findViewById(R.id.inamev)
        tvadditionalDescription = findViewById(R.id.adv)
        tvproductPrice = findViewById(R.id.ppricev)
        update = findViewById(R.id.update)
        delete = findViewById(R.id.delete)
    }

    private fun setValuesToViews() {
        tvproductId.text = intent.getStringExtra("proId")
        tvproductName.text = intent.getStringExtra("productName")
        tvproductCategory.text = intent.getStringExtra("productCategory")
        tvinnovatorName.text = intent.getStringExtra("innovatorName")
        tvadditionalDescription.text = intent.getStringExtra("additionalDescription")
        tvproductPrice.text = intent.getStringExtra("productPrice")
    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        proId: String,
        productName: String,
        productCategory: String,
        innovatorName: String,
        additionalDescription: String,
        productPrice: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_product, null)

        mDialog.setView(mDialogView)

        val productNameEditText = mDialogView.findViewById<EditText>(R.id.tvproductName)
        //productNameEditText.setText(productName)

        val productCategoryEditText = mDialogView.findViewById<EditText>(R.id.tvproductCategory)
        //productCategoryEditText.setText(productCategory)

        val innovatorNameEditText = mDialogView.findViewById<EditText>(R.id.uiname)
        //innovatorNameEditText.setText(innovatorName)

        val additionalDescriptionEditText = mDialogView.findViewById<EditText>(R.id.uad)
        //additionalDescriptionEditText.setText(additionalDescription)

        val productPriceEditText = mDialogView.findViewById<EditText>(R.id.upprice)
        //productPriceEditText.setText(productPrice)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.udata)

        productNameEditText.setText(intent.getStringExtra("productName").toString())
        productCategoryEditText.setText(intent.getStringExtra("productCategory").toString())
        innovatorNameEditText.setText(intent.getStringExtra("innovatorName").toString())
        additionalDescriptionEditText.setText(
            intent.getStringExtra("additionalDescription").toString()
        )
        productPriceEditText.setText(intent.getStringExtra("productPrice").toString())

        mDialog.setTitle("Updating $productName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateProductData(
                proId,
                productNameEditText.text.toString(),
                productCategoryEditText.text.toString(),
                innovatorNameEditText.text.toString(),
                additionalDescriptionEditText.text.toString(),
                productPriceEditText.text.toString()
            )
            Toast.makeText(applicationContext, "Product data updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvproductName.text = productNameEditText.text.toString()
            tvproductCategory.text = productCategoryEditText.text.toString()
            tvinnovatorName.text = innovatorNameEditText.text.toString()
            tvadditionalDescription.text = additionalDescriptionEditText.text.toString()
            tvproductPrice.text = productPriceEditText.text.toString()

            alertDialog.dismiss()
        }

    }
        private fun updateProductData(
            proId: String,
            productName: String,
            productCategory: String,
            innovatorName: String,
            additionalDescription: String,
            productPrice: String


        ){
            val dbRef = FirebaseDatabase.getInstance().getReference("Product").child(proId)
            val productInfo = ProductModel(proId, productName, productCategory, innovatorName, additionalDescription, productPrice)
            dbRef.setValue(productInfo)
        }


}

