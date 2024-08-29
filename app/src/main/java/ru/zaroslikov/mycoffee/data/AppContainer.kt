package ru.zaroslikov.mycoffee.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.zaroslikov.mycoffee.network.CoffeeApiService

interface AppContainer {
    val coffeeRepository: CoffeeRepository
}

class DefaultAppContainer : AppContainer {

//    private val BASE_URL =
//        "http://192.168.31.136:8080"

    private val BASE_URL =
        "http://10.0.2.2:8080/"


        private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()


    val retrofitService: CoffeeApiService by lazy {
        retrofit.create(CoffeeApiService::class.java)
    }

    override val coffeeRepository: CoffeeRepository by lazy {
        NetworkCoffeeRepository(retrofitService)
    }

}