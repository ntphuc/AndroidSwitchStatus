package com.switchstatus.ui.component.switches

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.switchstatus.ACTION_PUSH_NOTIFICATION
import com.switchstatus.LOG_TAG
import com.switchstatus.R
import com.switchstatus.TIME_DELAY_REFRESH
import com.switchstatus.data.Resource
import com.switchstatus.data.dto.switches.ItemStatus
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.data.dto.switches.Switches
import com.switchstatus.databinding.SwitchListActivityBinding
import com.switchstatus.ui.ViewModelFactory
import com.switchstatus.ui.base.BaseActivity
import com.switchstatus.ui.base.listeners.SwitchStatusListener
import com.switchstatus.ui.component.switches.adapter.SwitchesAdapter
import com.switchstatus.utils.observe
import com.switchstatus.utils.toGone
import com.switchstatus.utils.toVisible
import javax.inject.Inject


class SwitchesListActivity : BaseActivity(), SwitchStatusListener {

    private var switchesAdapter: SwitchesAdapter? = null
    private lateinit var binding: SwitchListActivityBinding

    @Inject
    lateinit var switchListViewModel: SwitchListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    var mainHandler: Handler = Handler()

    private val refreshView = object : Runnable {
        override fun run() {
            switchListViewModel.fetchListSwitches(false)
            mainHandler.postDelayed(this, TIME_DELAY_REFRESH)
        }
    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            when (intent?.action) {
                ACTION_PUSH_NOTIFICATION -> handleStatusChanged()

            }
        }
    }

    private fun handleStatusChanged() {
        Log.e(LOG_TAG, "handleStatusChanged")
        MaterialDialog(this).show {
            title(R.string.title_switch_off)
            message(R.string.message_switch_off)
            positiveButton(R.string.ok)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBroadcast()

        subcribeTopicNotify()

        val layoutManager = LinearLayoutManager(this)
        binding.rvSwitchesList.layoutManager = layoutManager
        binding.rvSwitchesList.setHasFixedSize(true)
        binding.rvSwitchesList.addItemDecoration(DividerItemDecoration(binding.rvSwitchesList.getContext(), DividerItemDecoration.VERTICAL))

        switchListViewModel.fetchListSwitches(true)
    }

    override fun onResume() {
        mainHandler.postDelayed(refreshView, TIME_DELAY_REFRESH)
        super.onResume()
    }

    override fun onPause() {
        mainHandler.removeCallbacks(refreshView)
        super.onPause()
    }

    private fun subcribeTopicNotify() {
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("switch")
                .addOnCompleteListener { task ->
                    var msg = getString(R.string.msg_subscribed)
                    if (!task.isSuccessful) {
                        msg = getString(R.string.msg_subscribe_failed)
                    }
                    Log.d(LOG_TAG, msg)
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }
        // [END subscribe_topics]

        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(LOG_TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                    Log.d(LOG_TAG, msg)
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()

                })
        // [END retrieve_current_token]
    }

    override fun initializeViewModel() {
        switchListViewModel = viewModelFactory.create(SwitchListViewModel::class.java)
    }

    override fun observeViewModel() {
        observe(switchListViewModel.switchesLiveData, ::handleSwitchesList)
        observe(switchListViewModel.statusLiveData, ::handleUpdateStatus)

    }

    private fun handleUpdateStatus(result: Resource<ItemStatus>) {
        when (result) {
            is Resource.Success -> result.data?.let {
              //  bindingNewStatus(it)
            }
            is Resource.DataError -> {
                result.errorCode?.let { switchListViewModel.showToastMessage(it) }
            }
        }

    }

    private fun bindingNewStatus(it: ItemStatus) {
        it.itemSwitch.status = it.status;
        binding.rvSwitchesList.adapter?.notifyDataSetChanged()
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
            if (switchesAdapter == null) {
                switchesAdapter = SwitchesAdapter(switchListViewModel, switches.switches)
                switchesAdapter!!.setOnSwitchStatusListener(this)
                binding.rvSwitchesList.adapter = switchesAdapter
            }else{
                switchesAdapter!!.setData(switches.switches)

            }
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

    override fun onSwitchStatusListener(item: ItemSwitch, newStatus: Boolean) {
        switchListViewModel.updateSwitchStatus(item, newStatus)

    }

}