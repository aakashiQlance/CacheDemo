package com.example.cachedemo.model

import com.example.cachedemo.model.Location
import com.example.cachedemo.model.Origin


data class CharResult(
    var created: String = "", // 2017-11-04T18:48:46.250Z
    var episode: List<String> = listOf(),
    var gender: String = "", // Male
    var id: Int = 0, // 1
    var image: String = "", // https://rickandmortyapi.com/api/character/avatar/1.jpeg
    var location: Location = Location(),
    var name: String = "", // Rick Sanchez
    var origin: Origin = Origin(),
    var species: String = "", // Human
    var status: String = "", // Alive
    var type: String = "",
    var url: String = "" // https://rickandmortyapi.com/api/character/1
)