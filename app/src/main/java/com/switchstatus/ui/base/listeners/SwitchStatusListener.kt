package com.switchstatus.ui.base.listeners

import com.switchstatus.data.dto.recipes.RecipesItem
import com.switchstatus.data.dto.switches.ItemSwitch

/**
 * Created by AhmedEltaher
 */

interface SwitchStatusListener {
    fun onSwitchStatusListener(item : ItemSwitch , newStatus: Boolean)
}
