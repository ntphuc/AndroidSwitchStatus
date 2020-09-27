package com.switchstatus.ui.component.switches

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.switchstatus.data.DataRepositorySource
import com.switchstatus.data.Resource
import com.switchstatus.data.dto.switches.ItemStatus
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.data.request.RequestUpdateStatus
import com.switchstatus.ui.base.BaseViewModel
import com.switchstatus.utils.SingleEvent
import com.switchstatus.utils.wrapEspressoIdlingResource
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

    val statusLiveDataPrivate = MutableLiveData<Resource<ItemStatus>>()
    val statusLiveData: LiveData<Resource<ItemStatus>> get() = statusLiveDataPrivate

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

    fun updateSwitchStatus(item: ItemSwitch, newStatus: Boolean) {
        val requestUpdateBody = RequestUpdateStatus()
        requestUpdateBody.status = newStatus
        viewModelScope.launch {
           // switchesLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestUpdateStatus(item, requestUpdateBody).collect {
                    statusLiveDataPrivate.value = it
                }
            }
        }

    }

}