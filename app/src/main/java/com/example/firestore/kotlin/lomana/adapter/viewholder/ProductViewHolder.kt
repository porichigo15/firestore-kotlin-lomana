package com.example.firestore.kotlin.lomana.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firestore.kotlin.lomana.R
import com.example.firestore.kotlin.lomana.model.Product

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val nameText: TextView = itemView.findViewById(R.id.nameText)
    private val priceText: TextView = itemView.findViewById(R.id.priceText)
    private val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    private val createDateText: TextView = itemView.findViewById(R.id.createDateText)
    private val updateDateText: TextView = itemView.findViewById(R.id.updateDateText)

    fun bindItem(product: Product) {
        itemView.apply {
            nameText.text = product.name
            priceText.text = "${product.price} Bath"
            descriptionText.text = product.description
            createDateText.text = product.create_date!!.toDate().toString()

            if (product.update_date != null) {
                updateDateText.text = product.update_date!!.toDate().toString()
            }
        }
    }
}