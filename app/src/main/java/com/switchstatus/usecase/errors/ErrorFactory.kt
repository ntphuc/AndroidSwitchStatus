package com.switchstatus.usecase.errors

import com.switchstatus.data.error.Error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
