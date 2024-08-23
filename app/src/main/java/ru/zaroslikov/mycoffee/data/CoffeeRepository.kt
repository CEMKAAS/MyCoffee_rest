package ru.zaroslikov.mycoffee.data

import ru.zaroslikov.mycoffee.model.Coffee
import ru.zaroslikov.mycoffee.network.CoffeeApiService

interface CoffeeRepository {
    suspend fun getCoffees():List<Coffee>
}

class NetworkCoffeeRepository(private val coffeeApiService: CoffeeApiService): CoffeeRepository {
    override suspend fun getCoffees(): List<Coffee>  = coffeeApiService.getCoffees()
}