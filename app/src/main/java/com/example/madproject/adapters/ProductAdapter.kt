package com.example.madproject.adapters

import android.view.LayoutInflater
import android.view.View
//import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.models.ProductModel

class ProductAdapter (private val productList: ArrayList<ProductModel>) :
        RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

        private lateinit var listener: OnItemClickListener

        interface OnItemClickListener{
                fun onItemClick(position: Int)
        }

        fun setOnItemClickListener(listener: OnItemClickListener) {
                this.listener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.pro_list_item,parent, false)
                return ViewHolder(itemView, listener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val currentProduct = productList[position]
                holder.tvproductName.text = currentProduct.productName
                holder.tvproductCategory.text = currentProduct.productCategory
                holder.tvinnovatorName.text = currentProduct.innovatorName
                holder.tvadditionalDescription.text =currentProduct.additionalDescription
                holder.tvproductPrice.text = currentProduct.productPrice
        }


        override fun getItemCount(): Int {
                return productList.size
        }

        class ViewHolder (itemView: View, listener: OnItemClickListener)   :
                RecyclerView.ViewHolder(itemView){

                val tvproductName : TextView = itemView.findViewById(R.id.tvproductName)
                val tvproductCategory : TextView = itemView.findViewById(R.id.tvproductCategory)
                val tvinnovatorName : TextView = itemView.findViewById(R.id.tvinnovatorName)
                val tvadditionalDescription : TextView = itemView.findViewById(R.id.tvadditionalDescription)
                val tvproductPrice : TextView = itemView.findViewById(R.id.tvproductPrice)

                init{
                        itemView.setOnClickListener {
                                listener.onItemClick(adapterPosition)
                        }
                }

        }


}