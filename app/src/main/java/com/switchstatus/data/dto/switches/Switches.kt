package com.switchstatus.data.dto.switches

import com.squareup.moshi.Json

data class Switches(
        @Json(name = "switches")
        val switches: List<ItemSwitch> = listOf()
)
