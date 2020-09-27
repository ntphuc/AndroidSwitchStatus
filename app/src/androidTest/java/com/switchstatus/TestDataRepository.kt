package com.switchstatus

import com.switchstatus.TestUtil.dataStatus
import com.switchstatus.TestUtil.initData
import com.switchstatus.data.DataRepositorySource
import com.switchstatus.data.Resource
import com.switchstatus.data.dto.login.LoginRequest
import com.switchstatus.data.dto.login.LoginResponse
import com.switchstatus.data.dto.recipes.Recipes
import com.switchstatus.data.error.Error
import com.switchstatus.data.error.NETWORK_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * Created by AhmedEltaher
 */

class TestDataRepository @Inject constructor() : DataRepositorySource {

    override suspend fun requestRecipes(): Flow<Resource<Recipes>> {
        return when (dataStatus) {
            DataStatus.Success -> {
                flow { emit(Resource.Success(initData())) }
            }
            DataStatus.Fail -> {
                flow { emit(Resource.DataError<Recipes>(errorCode = NETWORK_ERROR)) }
            }
            DataStatus.EmptyResponse -> {
                flow { emit(Resource.Success(Recipes(arrayListOf()))) }
            }
        }
    }

    override suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Success(LoginResponse("123", "Ahmed", "Mahmoud",
                    "FrunkfurterAlle", "77", "12000", "Berlin",
                    "Germany", "ahmed@ahmed.ahmed")))
        }
    }

    override suspend fun addToFavourite(id: String): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }

    override suspend fun removeFromFavourite(id: String): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }

    override suspend fun isFavourite(id: String): Flow<Resource<Boolean>> {
        return flow { emit(Resource.Success(true)) }
    }
}
