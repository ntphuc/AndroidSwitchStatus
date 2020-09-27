package com.switchstatus.data.dto.switches


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class ItemSwitch(
        @Json(name = "name")
        val name: String = "",
        @Json(name = "status")
        val status: Boolean = false,
        @Json(name = "card")
        val card: String = "",

) : Parcelable
