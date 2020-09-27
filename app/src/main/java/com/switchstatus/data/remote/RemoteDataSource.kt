package com.switchstatus.data.remote

import com.switchstatus.data.Resource
import com.switchstatus.data.dto.recipes.Recipes
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.data.request.RequestBodySwitches

/**
 * Created by AhmedEltaher
 */

internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<Recipes>
    suspend fun requestListSwitches(requestBodySwitches: RequestBodySwitches): Resource<Switches>

}
