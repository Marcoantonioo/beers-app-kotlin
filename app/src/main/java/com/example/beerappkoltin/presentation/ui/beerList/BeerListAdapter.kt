package com.example.beerappkoltin.presentation.ui.beerList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerappkoltin.R
import com.example.beerappkoltin.presentation.model.BeerView

class BeerListAdapter(
    private val context: Context
) : RecyclerView.Adapter<BeerListAdapter.ViewHolder>() {

    var list: List<BeerView> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.beer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.textViewName.text = item.name

        Glide.with(context)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.imageView);
    }

    override fun getItemCount() = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
