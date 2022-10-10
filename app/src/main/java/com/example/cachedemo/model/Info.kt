package com.example.cachedemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Info(
    var count: Int = 0, // 826
    var next: String = "", // https://rickandmortyapi.com/api/character/?page=2
    var pages: Int = 0, // 42
    var prev: String= ""// null
)