package com.rohan.shop

import android.app.Application
import com.rohan.shop.data.db.ProductDatabase
import com.rohan.shop.data.repository.ProductRepository
import com.rohan.shop.ui.productlist.ProductViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ShoppingApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ShoppingApplication))

        bind() from singleton { ProductDatabase(instance()) }
        bind() from singleton { ProductRepository(instance()) }
        bind() from provider { ProductViewModelFactory(instance()) }

    }
}