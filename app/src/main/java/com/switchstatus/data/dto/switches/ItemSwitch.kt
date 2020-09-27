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
        var status: Boolean = false,
        @Json(name = "displayName")
        val displayName: String = "",

) : Parcelable
