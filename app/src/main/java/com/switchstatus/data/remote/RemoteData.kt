package com.switchstatus.data.remote

import android.util.Log
import com.google.gson.JsonObject
import com.switchstatus.data.Resource
import com.switchstatus.data.dto.recipes.Recipes
import com.switchstatus.data.dto.recipes.RecipesItem
import com.switchstatus.data.dto.switches.ItemStatus
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.data.error.NETWORK_ERROR
import com.switchstatus.data.error.NO_INTERNET_CONNECTION
import com.switchstatus.data.remote.service.RecipesService
import com.switchstatus.data.request.BaseRequest
import com.switchstatus.data.request.RequestBodySwitches
import com.switchstatus.data.request.RequestUpdateStatus
import com.switchstatus.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


/**
 * Created by AhmedEltaher
 */

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {

//    override suspend fun requestListSwitches(requestBodySwitches: RequestBodySwitches): Resource<Switches> {
//        val recipesService = serviceGenerator.createService(RecipesService::class.java)
//
//        if (!networkConnectivity.isConnected()) {
//            return Resource.DataError(errorCode = NO_INTERNET_CONNECTION)
//
//        }
//
//        val call = recipesService.fetchSwitches(requestBodySwitches)
//
//        call.enqueue(object : Callback<Switches> {
//            override fun onResponse(call: Call<Switches>, response: Response<Switches>) {
//                val responseCode = response.code()
//
//                if (response.isSuccessful && response.body() != null) {
//                    Resource.Success(data = Switches(response.body()?.switches as ArrayList<ItemSwitch>))
//                } else {
//                    Resource.DataError(errorCode = responseCode)
//
//                }
//            }
//
//            override fun onFailure(call: Call<Switches>, t: Throwable) {
//               // Resource.DataError(errorCode = responseCode)
//
//            }
//        })
//
//
//    }


    override suspend fun requestListSwitches(requestBodySwitches: RequestBodySwitches): Resource<Switches> {
        val recipesService = serviceGenerator.createService(RecipesService::class.java)
        return when (val response = processCallParam(requestBodySwitches, recipesService::fetchSwitches)) {
            is Switches -> {
                Resource.Success(data = Switches(response.switches as ArrayList<ItemSwitch>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestRecipes(): Resource<Recipes> {
        val recipesService = serviceGenerator.createService(RecipesService::class.java)
        return when (val response = processCall(recipesService::fetchRecipes)) {
            is List<*> -> {
                Resource.Success(data = Recipes(response as ArrayList<RecipesItem>))
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }


    override suspend fun requestUpdateStatus(itemSwitch: ItemSwitch, requestUpdateStatus: RequestUpdateStatus): Resource<ItemStatus>  {
        val recipesService = serviceGenerator.createService(RecipesService::class.java)
        return when (val response = processCallUpdateStatus(itemSwitch, requestUpdateStatus, recipesService::updateStatus)) {
            is ItemStatus -> {
                Resource.Success(data = response as ItemStatus)
            } else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

    private suspend fun processCallParam(request: BaseRequest, responseCall: suspend (String) -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(request.toJSON()!!)
            Log.e("Phuc", response.message() +" "+ response.code())
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

    private suspend fun processCallUpdateStatus(itemSwitch: ItemSwitch, request: BaseRequest, responseCall: suspend (String, JsonObject) -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        //val body: String = "{\"status\":false}";
        val jsonObject = JsonObject()
        jsonObject.addProperty("status", !itemSwitch.status)
        return try {
            val response = responseCall.invoke(itemSwitch.name, jsonObject)
            val responseCode = response.code()
            if (response.isSuccessful) {
                ItemStatus(itemSwitch, (request as RequestUpdateStatus).status)
            } else {
                responseCode
            }

        } catch (e: Exception) {
            NETWORK_ERROR
        }
    }

}
