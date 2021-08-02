package com.example.firestore.kotlin.lomana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firestore.kotlin.lomana.adapter.ProductAdapter
import com.example.firestore.kotlin.lomana.model.Product
import com.example.firestore.kotlin.lomana.viewmodel.ProductViewModel
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {

    private lateinit var name: EditText
    private lateinit var price: EditText
    private lateinit var description: EditText
    private lateinit var submit: Button
    private lateinit var rvList: RecyclerView

    private lateinit var productAdapter: ProductAdapter
    private lateinit var list: ArrayList<Product>

    private var selected: Product = Product()

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElement()
        initViewModel()
    }

    private fun initElement() {
        name = findViewById(R.id.name)
        price = findViewById(R.id.price)
        description = findViewById(R.id.description)
        submit = findViewById(R.id.submit)
        rvList = findViewById(R.id.rvList)

        list = ArrayList()

        submit.setOnClickListener {
            create()
        }

        // Get list
        productViewModel.getList()

    }

    private fun initViewModel() {
        productViewModel.createLiveData.observe(this, {
            onCreate(it)
        })

        productViewModel.updateLiveData.observe(this, {
            onUpdate(it)
        })

        productViewModel.deleteLiveData.observe(this, {
            onDelete(it)
        })

        productViewModel.getListLiveData.observe(this, {
            onGetList(it)
        })
    }

    private fun onCreate(it: Boolean) {
        if (it) {
            productViewModel.getList()
            resetText()
        }
    }

    private fun onUpdate(it: Boolean) {
        if (it) {
            productViewModel.getList()
            resetText()
        }
    }

    private fun onDelete(it: Boolean) {
        if (it) {
            productViewModel.getList()
            resetText()
        }
    }

    private fun onGetList(it: List<Product>) {
        list = ArrayList()
        list.addAll(it)

        productAdapter = ProductAdapter(list, this)

        rvList.adapter = productAdapter
        rvList.layoutManager = LinearLayoutManager(baseContext)

        productAdapter.notifyDataSetChanged()
    }

    private fun create() {
        val product = Product(
            selected.id,
            name.text.toString(),
            price.text.toString().toDouble(),
            description.text.toString(),
            selected.create_date ?: Timestamp.now(),
            selected.update_date
        )
        if (product.id != null) {
            productViewModel.update(product)
        } else {
            productViewModel.create(product)
        }
    }

    private fun resetText() {
        selected = Product()

        name.text = null
        price.text = null
        description.text = null
    }

    override fun onClick(item: Product, position: Int) {
        selected = item
        selected.update_date = Timestamp.now()

        name.setText(selected.name)
        price.setText(selected.price.toString())
        description.setText(selected.description)
    }

    override fun onDelete(item: Product, position: Int) {
        productViewModel.delete(item.id!!)
    }
}

