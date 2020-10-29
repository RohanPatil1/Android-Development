package com.rohan.shop.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rohan.shop.data.db.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDAO(): ProductDAO


    //Create single instance of ProductDatabase
    companion object {
        @Volatile //Only one thread can access/create it at a time (if tried simultaneously)
        private var instance: ProductDatabase? = null
        private val LOCK = Any() //passed in synchronised so that once a thread is using,none can!

        //Whenever ProductDatabase() constructor is called,this operator function will be called.
        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: createDatabase(
                    context
                )
                    .also { instance = it }
        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java, "ProductDB.db"
            ).build()

    }

}