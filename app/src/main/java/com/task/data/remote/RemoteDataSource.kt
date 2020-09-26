package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.recipes.Recipes
import com.task.data.dto.switches.Switches
import com.task.data.request.RequestBodySwitches

/**
 * Created by AhmedEltaher
 */

internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<Recipes>
    suspend fun requestListSwitches(requestBodySwitches: RequestBodySwitches): Resource<Switches>

}
