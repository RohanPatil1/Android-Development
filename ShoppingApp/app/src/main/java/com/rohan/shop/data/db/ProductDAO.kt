package com.rohan.shop.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rohan.shop.data.db.entity.Product

@Dao
interface ProductDAO {

    //Insert  + Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Product)

    @Delete
    suspend fun delete(item: Product)

    @Query("SELECT  * FROM  products")
    fun getAllProducts(): LiveData<List<Product>>


}