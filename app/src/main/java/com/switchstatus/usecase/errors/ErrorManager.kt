package com.switchstatus.usecase.errors

import com.switchstatus.data.error.Error
import com.switchstatus.data.error.mapper.ErrorMapper
import javax.inject.Inject

/**
 * Created by AhmedEltaher
 */

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorFactory {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
