package com.hectorcorsua.beerfinder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hectorcorsua.beerfinder.R
import com.hectorcorsua.beerfinder.models.Beer
import com.squareup.picasso.Picasso

class BeerAdapter(private val beers : List<Beer>, private var listener: OnBeerClickListener) :
    RecyclerView.Adapter<BeerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater, parent, listener)
    }
    override fun getItemCount(): Int = beers.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = beers[position]
        holder.bind(item)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, onBeerClickListener: OnBeerClickListener) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_beer, parent, false)){

        private var elementClickListener : OnBeerClickListener? = null
        private var beer : Beer? = null

        private var tvName : TextView = itemView.findViewById(R.id.beer_name)
        private var ivImage : ImageView = itemView.findViewById(R.id.beer_image)
        private var tvAbv : TextView = itemView.findViewById(R.id.beer_abv)
        private var tvIbu : TextView = itemView.findViewById(R.id.beer_ibu)
        private var tvEbc : TextView = itemView.findViewById(R.id.beer_ebc)

        init {
            elementClickListener = onBeerClickListener
            itemView.findViewById<View>(R.id.beer_row).setOnClickListener {
                elementClickListener?.onElementClick(beer!!)
            }
        }

        fun bind(beer : Beer)
        {
            this.beer = beer
            tvName.text = beer.name
            Picasso.get()
                .load(beer.image)
                .resize(100,100)
                .centerInside()
                .into(ivImage)
            tvAbv.text = beer.abv.toString()
            tvIbu.text = beer.ibu.toString()
            tvEbc.text = beer.ebc.toString()
        }
    }
}

interface OnBeerClickListener{
    fun onElementClick(beer: Beer)
}