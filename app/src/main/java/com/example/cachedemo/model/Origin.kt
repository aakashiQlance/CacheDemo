package com.example.cachedemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



data class Origin(
    var name: String = "", // Earth (C-137)
    var url: String = "" // https://rickandmortyapi.com/api/location/1
)