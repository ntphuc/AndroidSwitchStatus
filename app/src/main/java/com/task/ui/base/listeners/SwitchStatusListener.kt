package com.task.ui.base.listeners

import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.switches.ItemSwitch

/**
 * Created by AhmedEltaher
 */

interface SwitchStatusListener {
    fun onSwitchStatusListener(item : ItemSwitch , newStatus: Boolean)
}
