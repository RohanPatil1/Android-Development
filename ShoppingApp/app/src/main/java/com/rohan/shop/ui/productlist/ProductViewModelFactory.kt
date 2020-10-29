package com.rohan.shop.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rohan.shop.data.repository.ProductRepository

@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(repository) as T
    }
}
