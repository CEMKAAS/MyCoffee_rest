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

    private val BASE_URL =
        "http://localhost:8080/api/"

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