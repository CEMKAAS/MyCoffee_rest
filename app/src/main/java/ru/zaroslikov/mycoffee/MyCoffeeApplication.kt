package ru.zaroslikov.mycoffee

import android.app.Application
import ru.zaroslikov.mycoffee.data.AppContainer
import ru.zaroslikov.mycoffee.data.DefaultAppContainer

class MyCoffeeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}