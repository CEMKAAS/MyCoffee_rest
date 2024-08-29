package ru.zaroslikov.mycoffee.model

import kotlinx.serialization.Serializable


@Serializable
data class CoffeeDTO (val name: String, val count: Int)