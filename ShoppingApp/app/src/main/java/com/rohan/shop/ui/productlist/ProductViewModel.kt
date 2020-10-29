package com.rohan.shop.ui.productlist

import androidx.lifecycle.ViewModel
import com.rohan.shop.data.db.entity.Product
import com.rohan.shop.data.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    fun upsert(item: Product) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: Product) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllProducts() = repository.getAllProducts()


}