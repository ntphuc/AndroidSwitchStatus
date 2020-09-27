package com.switchstatus.ui.base.listeners

import com.switchstatus.data.dto.recipes.RecipesItem

/**
 * Created by AhmedEltaher
 */

interface RecyclerItemListener {
    fun onItemSelected(recipe : RecipesItem)
}
