package fr.delcey.mvctomvvm.ui.list

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.delcey.mvctomvvm.R
import fr.delcey.mvctomvvm.data.pokemon.PokemonResponse

class PokemonAdapter(private val listener: (PokemonResponse) -> Unit) :
    ListAdapter<PokemonResponse, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PokemonViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.pokemon_itemview, parent, false)
    )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pokemonCardView = itemView.findViewById<CardView>(R.id.pokemon_itemview_cardview)
        private val pokemonImageView = itemView.findViewById<ImageView>(R.id.pokemon_itemview_iv)
        private val pokemonNameTextView = itemView.findViewById<TextView>(R.id.pokemon_itemview_tv_name)
        private val pokemonType1TextView = itemView.findViewById<TextView>(R.id.pokemon_itemview_tv_type1)
        private val pokemonType2TextView = itemView.findViewById<TextView>(R.id.pokemon_itemview_tv_type2)

        fun bind(pokemonResponse: PokemonResponse, listener: (PokemonResponse) -> Unit) {
            Glide.with(pokemonImageView)
                .load(pokemonResponse.sprites?.frontDefault)
                .fitCenter()
                .into(pokemonImageView)

            pokemonNameTextView.text = pokemonResponse.getDetailedName()

            // TODO Intelligence on the Adapter : can't be tested
            val firstType = pokemonResponse.types?.firstOrNull {
                it?.slot == 1
            }?.type
            pokemonType1TextView.isVisible = firstType != null
            if (firstType != null) {
                pokemonType1TextView.text = firstType.name
                pokemonType1TextView.backgroundTintList = ColorStateList.valueOf(firstType.getColorInt())
            }

            // TODO Should be refactored but it doesn't makes much sense, the refactored function would be, for example :
            //  updatePokemonChip(pokemonResponse, 2, pokemonType2TextView)
            val secondType = pokemonResponse.types?.firstOrNull {
                it?.slot == 2
            }?.type
            pokemonType2TextView.isVisible = secondType != null
            if (secondType != null) {
                pokemonType2TextView.text = secondType.name
                pokemonType2TextView.backgroundTintList = ColorStateList.valueOf(secondType.getColorInt())
            }

            pokemonCardView.setOnClickListener { listener(pokemonResponse) }
        }
    }

    private class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonResponse>() {
        override fun areItemsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse) = oldItem == newItem
    }
}