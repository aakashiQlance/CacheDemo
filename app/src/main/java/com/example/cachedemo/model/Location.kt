package com.example.cachedemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Location(
    var name: String = "", // Citadel of Ricks
    var url: String = "" // https://rickandmortyapi.com/api/location/3
)