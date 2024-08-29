package ru.zaroslikov.mycoffee.data

import ru.zaroslikov.mycoffee.model.Coffee
import ru.zaroslikov.mycoffee.model.CoffeeDTO
import ru.zaroslikov.mycoffee.network.CoffeeApiService

interface CoffeeRepository {
    suspend fun getCoffees():List<Coffee>
    suspend fun postCoffees(coffee: CoffeeDTO)
    suspend fun getCoffee( id: Int)
    suspend fun deleteCoffee( id: Int)
}

class NetworkCoffeeRepository(private val coffeeApiService: CoffeeApiService): CoffeeRepository {
    override suspend fun getCoffees(): List<Coffee>  = coffeeApiService.getCoffees()

    override suspend fun postCoffees(coffee: CoffeeDTO) = coffeeApiService.postCoffees(coffee)
    override suspend fun getCoffee(id: Int) = coffeeApiService.getCoffee(id)

    override suspend fun deleteCoffee(id: Int) = coffeeApiService.deleteCoffee(id)
}