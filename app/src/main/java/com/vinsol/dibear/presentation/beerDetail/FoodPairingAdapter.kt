package com.vinsol.dibear.presentation.beerDetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vinsol.dibear.R

import kotlinx.android.synthetic.main.list_item_food_pairing.view.*

class FoodPairingAdapter: ListAdapter<String, FoodPairingViewHolder>(FoodPairingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): FoodPairingViewHolder {
        return FoodPairingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_food_pairing, parent, false))
    }

    override fun onBindViewHolder(vh: FoodPairingViewHolder, position: Int) {
        vh.bind(getItem(position))
    }
}

class FoodPairingDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(p0: String, p1: String) =  p0 == p1
    override fun areContentsTheSame(p0: String, p1: String) = p0 == p1
}

class FoodPairingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(text: String) {
        itemView.list_item_food_pairing_tv.text = text
    }
}