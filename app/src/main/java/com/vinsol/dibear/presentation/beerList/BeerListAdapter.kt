package com.vinsol.dibear.presentation.beerList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinsol.dibear.R
import com.vinsol.dibear.data.entities.BeerData
import com.vinsol.dibear.presentation.common.ImageLoader

import kotlinx.android.synthetic.main.list_item_top_beers.view.*

class TopBeersAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (BeerData) -> Unit
    ): RecyclerView.Adapter<TopBeersViewHolder>() {

    private val beerList: MutableList<BeerData> = mutableListOf()

    override fun onCreateViewHolder(parentView: ViewGroup, viewType: Int): TopBeersViewHolder {
        val view = LayoutInflater.from(parentView.context).inflate(R.layout.list_item_top_beers, parentView, false)
        return TopBeersViewHolder(view)
    }

    override fun getItemCount(): Int = beerList.count()

    override fun onBindViewHolder(vh: TopBeersViewHolder, position: Int) {
        vh.bind(beerList[position], imageLoader, clickListener)
    }

    fun addItems(list: List<BeerData>) {
        beerList.clear()
        beerList.addAll(list)
        notifyDataSetChanged()
    }
}

class TopBeersViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(beerData: BeerData, imageLoader: ImageLoader, clickListener: (BeerData) -> Unit) {

        itemView.beer_abv.text = itemView.context.getString(R.string.abv_val, beerData.abv.toString())
        itemView.beer_title.text = beerData.name
        imageLoader.load(beerData.imageUrl, itemView.beer_image)

        itemView.setOnClickListener { clickListener(beerData) }
    }
}