package com.task.ui.component.switches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.recipes.RecipesItem
import com.task.data.dto.switches.ItemSwitch
import com.task.databinding.RecipeItemBinding
import com.task.databinding.SwitchItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.base.listeners.SwitchStatusListener
import com.task.ui.component.recipes.RecipesListViewModel
import com.task.ui.component.switches.SwitchListViewModel

/**
 * Created by AhmedEltaher
 */

class SwitchesAdapter(private val switchListViewModel: SwitchListViewModel, private val switches: List<ItemSwitch>) : RecyclerView.Adapter<SwitchViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: RecipesItem) {
           // switchListViewModel.openRecipeDetails(recipe)
        }
    }

    private val onSwitchStatusListener: SwitchStatusListener = object : SwitchStatusListener {
        override fun onSwitchStatusListener(item: ItemSwitch, newStatus: Boolean) {
            TODO("Not yet implemented")
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
}

