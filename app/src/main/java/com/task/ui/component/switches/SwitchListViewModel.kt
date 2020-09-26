package com.task.ui.component.switches

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.data.DataRepositorySource
import com.task.data.Resource
import com.task.data.dto.switches.Switches
import com.task.ui.base.BaseViewModel
import com.task.utils.SingleEvent
import com.task.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SwitchListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel()  {


    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val switchesLiveDataPrivate = MutableLiveData<Resource<Switches>>()
    val switchesLiveData: LiveData<Resource<Switches>> get() = switchesLiveDataPrivate


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate
    
    fun fetchListSwitches() {
        viewModelScope.launch {
            switchesLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestListSwitches().collect {
                    switchesLiveDataPrivate.value = it
                }
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

}