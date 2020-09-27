package com.switchstatus.data.remote.service

import com.switchstatus.data.dto.recipes.RecipesItem
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.data.request.BaseRequest
import com.switchstatus.data.request.RequestBodySwitches
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by AhmedEltaher
 */

interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<List<RecipesItem>>
    @POST("OpenAPI/Services/GetSwitches")
    suspend fun fetchSwitches(@Body bodySwitches: BaseRequest): Response<Switches>

}
