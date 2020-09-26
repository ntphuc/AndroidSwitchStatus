package com.task.ui.component.switches.adapter

import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.switches.ItemSwitch
import com.task.databinding.RecipeItemBinding
import com.task.databinding.SwitchItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.base.listeners.SwitchStatusListener

/**
 * Created by AhmedEltaher
 */

class SwitchViewHolder(private val itemBinding: SwitchItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(switch: ItemSwitch, switchStatusListener: SwitchStatusListener) {
        itemBinding.tvName.text = switch.name
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


