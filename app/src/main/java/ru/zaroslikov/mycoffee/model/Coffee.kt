package ru.zaroslikov.mycoffee.model

import kotlinx.serialization.Serializable

@Serializable
data class Coffee(val id: Long, val name: String, val count: Int)
