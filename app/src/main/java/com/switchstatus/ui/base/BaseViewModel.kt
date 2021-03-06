package com.switchstatus.ui.base

import androidx.lifecycle.ViewModel
import com.switchstatus.data.error.mapper.ErrorMapper
import com.switchstatus.usecase.errors.ErrorManager


/**
 * Created by AhmedEltaher
 */


abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    val errorManager: ErrorManager = ErrorManager(ErrorMapper())
}
