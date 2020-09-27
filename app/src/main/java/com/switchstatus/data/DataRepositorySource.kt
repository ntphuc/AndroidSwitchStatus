package com.switchstatus.data

import com.switchstatus.data.dto.recipes.Recipes
import com.switchstatus.data.dto.login.LoginRequest
import com.switchstatus.data.dto.login.LoginResponse
import com.switchstatus.data.dto.switches.Switches
import kotlinx.coroutines.flow.Flow

/**
 * Created by AhmedEltaher
 */

interface DataRepositorySource {
    suspend fun requestListSwitches(): Flow<Resource<Switches>>
    suspend fun requestRecipes(): Flow<Resource<Recipes>>
    suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    suspend fun addToFavourite(id: String): Flow<Resource<Boolean>>
    suspend fun removeFromFavourite(id: String): Flow<Resource<Boolean>>
    suspend fun isFavourite(id: String): Flow<Resource<Boolean>>
}
