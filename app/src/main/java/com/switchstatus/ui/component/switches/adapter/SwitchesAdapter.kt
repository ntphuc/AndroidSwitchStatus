package com.switchstatus.ui.component.switches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.switchstatus.data.dto.recipes.RecipesItem
import com.switchstatus.data.dto.switches.ItemSwitch
import com.switchstatus.databinding.RecipeItemBinding
import com.switchstatus.databinding.SwitchItemBinding
import com.switchstatus.ui.base.listeners.RecyclerItemListener
import com.switchstatus.ui.base.listeners.SwitchStatusListener
import com.switchstatus.ui.component.recipes.RecipesListViewModel
import com.switchstatus.ui.component.switches.SwitchListViewModel

/**
 * Created by AhmedEltaher
 */

class SwitchesAdapter(private val switchListViewModel: SwitchListViewModel, private val switches: List<ItemSwitch>) : RecyclerView.Adapter<SwitchViewHolder>() {

    private var mSwitchStatusListener: SwitchStatusListener? = null
    
    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: RecipesItem) {
           // switchListViewModel.openRecipeDetails(recipe)
        }
    }

    private val onSwitchStatusListener: SwitchStatusListener = object : SwitchStatusListener {
        override fun onSwitchStatusListener(item: ItemSwitch, newStatus: Boolean) {
            mSwitchStatusListener?.onSwitchStatusListener(item, newStatus)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwitchViewHolder {
        val itemBinding = SwitchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwitchViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SwitchViewHolder, position: Int) {
        holder.bind(switches[position], onSwitchStatusListener)
    }

    override fun getItemCount(): Int {
        return switches.size
    }

    fun setOnSwitchStatusListener(listener: SwitchStatusListener) {
        this.mSwitchStatusListener = listener;
    }
}

