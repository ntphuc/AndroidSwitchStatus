package com.switchstatus.data

import com.switchstatus.data.dto.login.LoginRequest
import com.switchstatus.data.dto.login.LoginResponse
import com.switchstatus.data.dto.recipes.Recipes
import com.switchstatus.data.dto.switches.ItemStatus
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.data.local.LocalData
import com.switchstatus.data.remote.RemoteData
import com.switchstatus.data.request.RequestBodySwitches
import com.switchstatus.data.request.RequestUpdateStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by AhmedEltaher
 */

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {


    override suspend fun requestListSwitches(): Flow<Resource<Switches>> {
        return flow {
            emit(remoteRepository.requestListSwitches(RequestBodySwitches()))
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestRecipes(): Flow<Resource<Recipes>> {
        return flow {
            emit(remoteRepository.requestRecipes())
        }.flowOn(ioDispatcher)
    }

    override suspend fun doLogin(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            emit(localRepository.doLogin(loginRequest))
        }.flowOn(ioDispatcher)
    }

    override suspend fun addToFavourite(id: String): Flow<Resource<Boolean>> {
        return flow {
            localRepository.getCachedFavourites().let {
                it.data?.toMutableSet()?.let { set ->
                    val isAdded = set.add(id)
                    if (isAdded) {
                        emit(localRepository.cacheFavourites(set))
                    } else {
                        emit(Resource.Success(false))
                    }
                }
                it.errorCode?.let { errorCode ->
                    emit(Resource.DataError<Boolean>(errorCode))
                }
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun removeFromFavourite(id: String): Flow<Resource<Boolean>> {
        return flow {
            emit(localRepository.removeFromFavourites(id))
        }.flowOn(ioDispatcher)
    }

    override suspend fun isFavourite(id: String): Flow<Resource<Boolean>> {
        return flow {
            emit(localRepository.isFavourite(id))
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestUpdateStatus(itemSwitch: ItemSwitch, requestUpdateStatus: RequestUpdateStatus): Flow<Resource<ItemStatus>> {
        return flow {
            emit(remoteRepository.requestUpdateStatus(itemSwitch,requestUpdateStatus))
        }.flowOn(ioDispatcher)
    }
}
