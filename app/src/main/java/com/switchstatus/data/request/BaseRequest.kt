package com.switchstatus.data.request

import com.google.gson.Gson
import com.google.gson.GsonBuilder

open class BaseRequest {
    open fun toJSON(): String? {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this)
    }
}
