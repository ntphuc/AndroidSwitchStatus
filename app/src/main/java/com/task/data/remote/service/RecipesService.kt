package com.task.data.remote.service

import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.switches.ItemSwitch
import com.task.data.dto.switches.Switches
import com.task.data.request.BaseRequest
import com.task.data.request.RequestBodySwitches
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
    suspend fun fetchSwitches(): Response<Switches>

}
