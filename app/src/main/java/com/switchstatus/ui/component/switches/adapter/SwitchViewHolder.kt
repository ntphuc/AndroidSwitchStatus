package com.switchstatus.ui.component.switches.adapter

import androidx.recyclerview.widget.RecyclerView
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.databinding.SwitchItemBinding
import com.switchstatus.ui.base.listeners.SwitchStatusListener


/**
 * Created by AhmedEltaher
 */

class SwitchViewHolder(private val itemBinding: SwitchItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(switch: ItemSwitch, switchStatusListener: SwitchStatusListener) {
        itemBinding.tvName.text = switch.displayName
        itemBinding.switchStatus.isOn = switch.status

        itemBinding.switchStatus.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                switchStatusListener.onSwitchStatusListener(switch, isOn)
            }
        })

//        itemBinding.switchStatus.setOnCheckedChangeListener { buttonView, isChecked ->
//            switchStatusListener.onSwitchStatusListener(switch, isChecked)
//            if (isChecked) {
//                // The switch is enabled/checked
//            } else {
//                // The switch is disabled
//            }
//        }
    }
}


