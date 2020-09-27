package com.switchstatus.ui.component.recipes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.switchstatus.R
import com.switchstatus.data.dto.recipes.RecipesItem
import com.switchstatus.databinding.RecipeItemBinding
import com.switchstatus.ui.base.listeners.RecyclerItemListener

/**
 * Created by AhmedEltaher
 */

class RecipeViewHolder(private val itemBinding: RecipeItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: RecipesItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvCaption.text = recipesItem.description
        itemBinding.tvName.text = recipesItem.name
        Picasso.get().load(recipesItem.thumb).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(itemBinding.ivRecipeItemImage)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(recipesItem) }
    }
}

