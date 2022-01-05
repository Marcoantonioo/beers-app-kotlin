package com.example.beerappkoltin

import android.app.Application
import com.example.beerappkoltin.data.dataModule
import com.example.beerappkoltin.domain.domainModule
import com.example.beerappkoltin.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BeerAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BeerAppApplication)
            modules(
                domainModule,
                dataModule,
                presentationModule,
            )
        }
    }
}
