package com.example.madproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.adapters.ProductAdapter
import com.example.madproject.models.ProductModel
import com.google.firebase.database.*

class MainActivity2 : AppCompatActivity() {


    private lateinit var productRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var productList: ArrayList<ProductModel>
    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        productRecyclerView = findViewById(R.id.rvproduct)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        productList = arrayListOf<ProductModel>()

        getproductData()

    }

    private fun getproductData(){
        productRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Product")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                if (snapshot.exists()){
                    for(productSnap in snapshot.children){
                        val productData = productSnap.getValue(ProductModel::class.java)
                        productList.add(productData!!)
                    }
                    val mAdapter = ProductAdapter(productList)
                    productRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MainActivity2, ProductView::class.java)

                            //put extras
                            intent.putExtra("proId", productList[position].proId)
                            intent.putExtra("productName", productList[position].productName)
                            intent.putExtra("productCategory", productList[position].productCategory)
                            intent.putExtra("innovatorName", productList[position].innovatorName)
                            intent.putExtra("additionalDescription", productList[position].additionalDescription)
                            intent.putExtra("productPrice", productList[position].productPrice)
                            startActivity(intent)
                        }

                    })

                    productRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}