package ru.zaroslikov.mycoffee.model

import kotlinx.serialization.Serializable

@Serializable
data class Coffee( val id : Int, val name: String, val count: Int)
