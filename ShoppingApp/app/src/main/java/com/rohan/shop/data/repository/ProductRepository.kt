package com.rohan.shop.data.repository

import com.rohan.shop.data.db.ProductDatabase
import com.rohan.shop.data.db.entity.Product

class ProductRepository(
    private val db: ProductDatabase
) {

    suspend fun upsert(item: Product) = db.getProductDAO().upsert(item)

    suspend fun delete(item: Product) = db.getProductDAO().delete(item)

    fun getAllProducts() = db.getProductDAO().getAllProducts()


}
