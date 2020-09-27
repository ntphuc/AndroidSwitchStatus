package com.switchstatus.ui.component.switches

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.switchstatus.ACTION_PUSH_NOTIFICATION
import com.switchstatus.LOG_TAG
import com.switchstatus.data.Resource
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.databinding.SwitchListActivityBinding
import com.switchstatus.ui.ViewModelFactory
import com.switchstatus.ui.base.BaseActivity
import com.switchstatus.ui.component.switches.adapter.SwitchesAdapter
import com.switchstatus.utils.observe
import com.switchstatus.utils.toGone
import com.switchstatus.utils.toVisible
import javax.inject.Inject

class SwitchesListActivity : BaseActivity() {

    private lateinit var switchesAdapter: SwitchesAdapter
    private lateinit var binding: SwitchListActivityBinding

    @Inject
    lateinit var switchListViewModel: SwitchListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            when (intent?.action) {
                ACTION_PUSH_NOTIFICATION -> handleStatusChanged()

            }
        }
    }

    private fun handleStatusChanged() {
        Log.e(LOG_TAG, "handleStatusChanged")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBroadcast()

        val layoutManager = LinearLayoutManager(this)
        binding.rvSwitchesList.layoutManager = layoutManager
        binding.rvSwitchesList.setHasFixedSize(true)

        switchListViewModel.fetchListSwitches()
    }

    override fun initializeViewModel() {
        switchListViewModel = viewModelFactory.create(SwitchListViewModel::class.java)
    }

    override fun observeViewModel() {
        observe(switchListViewModel.switchesLiveData, ::handleSwitchesList)
    }

    override fun initViewBinding() {
        binding = SwitchListActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)    
    }

    private fun registerBroadcast() {

        val intentFilter = IntentFilter(ACTION_PUSH_NOTIFICATION)
        registerReceiver(broadCastReceiver, intentFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(broadCastReceiver)
        super.onDestroy()
    }

    private fun handleSwitchesList(status: Resource<Switches>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(switches = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { switchListViewModel.showToastMessage(it) }
            }
        }
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvSwitchesList.toGone()
    }

    private fun bindListData(switches: Switches) {
        if (!(switches.switches.isNullOrEmpty())) {
            switchesAdapter = SwitchesAdapter(switchListViewModel, switches.switches)
            binding.rvSwitchesList.adapter = switchesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvSwitchesList.visibility = if (show) View.VISIBLE else View.GONE
        binding.pbLoading.toGone()
    }

}