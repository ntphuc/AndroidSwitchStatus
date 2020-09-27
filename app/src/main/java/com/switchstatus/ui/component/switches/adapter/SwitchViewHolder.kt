package com.switchstatus.ui.component.switches.adapter

import androidx.recyclerview.widget.RecyclerView
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.databinding.SwitchItemBinding
import com.switchstatus.ui.base.listeners.SwitchStatusListener

/**
 * Created by AhmedEltaher
 */

class SwitchViewHolder(private val itemBinding: SwitchItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(switch: ItemSwitch, switchStatusListener: SwitchStatusListener) {
        itemBinding.tvName.text = switch.displayName
        itemBinding.switchStatus.isChecked = switch.status
        itemBinding.switchStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            switchStatusListener.onSwitchStatusListener(switch, isChecked)
            if (isChecked) {
                // The switch is enabled/checked
            } else {
                // The switch is disabled
            }
        }
    }
}


