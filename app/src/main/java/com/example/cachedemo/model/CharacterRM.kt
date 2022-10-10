package com.example.cachedemo.model



data class CharacterRM(
    var created: String = "", // 2017-11-04T18:50:21.651Z
    var episode: List<String> = listOf(),
    var gender: String = "", // Male
    var id: Int = 0, // 2
    var image: String = "", // https://rickandmortyapi.com/api/character/avatar/2.jpeg
    var location: Location = Location(),
    var name: String = "", // Morty Smith
    var origin: Origin = Origin(),
    var species: String = "", // Human
    var status: String = "", // Alive
    var type: String = "",
    var url: String = "" // https://rickandmortyapi.com/api/character/2
)