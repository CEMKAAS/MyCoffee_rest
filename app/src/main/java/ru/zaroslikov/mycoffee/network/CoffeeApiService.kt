package ru.zaroslikov.mycoffee.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.zaroslikov.mycoffee.model.Coffee
import ru.zaroslikov.mycoffee.model.CoffeeDTO

interface CoffeeApiService {
    @GET("coffees")
    suspend fun getCoffees(): List<Coffee>
    @GET("coffees/{id}")
    suspend fun getCoffee(@Path("id") id: Int)

    @POST("coffees")
    suspend fun postCoffees(@Body coffee: CoffeeDTO)

    @DELETE("coffees/{id}")
    suspend fun deleteCoffee(@Path("id") id: Int)
    
}