package com.switchstatus.data.remote.service

import com.switchstatus.data.dto.recipes.RecipesItem
import com.switchstatus.data.dto.switches.Switches
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by AhmedEltaher
 */

interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<List<RecipesItem>>
    @POST("OpenAPI/Services/GetSwitches")
    suspend fun fetchSwitches(@Body bodySwitches: String): Response<Switches>
    @PUT("{itemName}/Properties/status")
    suspend fun updateStatus(@Path("itemName") itemName : String, @Body body: String): Response<Boolean>

}
