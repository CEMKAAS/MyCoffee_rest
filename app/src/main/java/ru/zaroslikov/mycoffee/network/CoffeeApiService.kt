package ru.zaroslikov.mycoffee.network

import retrofit2.http.GET
import ru.zaroslikov.mycoffee.model.Coffee

interface CoffeeApiService {
    @GET("Coffees")
    suspend fun getCoffees(): List<Coffee>
    
}