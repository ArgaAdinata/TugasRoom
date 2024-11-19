package com.example.tugasroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasroom.database.CharEntity
import com.example.tugasroom.database.CharRoomDatabase
import com.example.tugasroom.databinding.ItemCharBinding
import com.example.tugasroom.model.Chars
import com.squareup.picasso.Picasso

typealias OnClickChar = (Chars) -> Unit

class CharAdapter(
    private val listChar: List<Chars>,
    private val onClick: OnClickChar,
    private val database: CharRoomDatabase?
) : RecyclerView.Adapter<CharAdapter.itemCharsViewHolder>() {

    inner class itemCharsViewHolder(private val binding: ItemCharBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Chars) {
            with(binding) {
                txtName.text = data.artistName
                Picasso.Builder(itemView.context).build()
                    .load(data.url)
                    .fit()
                    .centerCrop()
                    .into(imgChar)

                // Observe favorite status
                database?.charDao()?.isCharFavorite(data.artistName)?.observeForever { isFavorite ->
                    imgLove.setImageResource(
                        if (isFavorite) R.drawable.baseline_favorite_24
                        else R.drawable.baseline_favorite_border_24
                    )
                }

                // Handle favorite button click
                imgLove.setOnClickListener {
                    Thread {
                        val dao = database?.charDao()
                        val existingChar = dao?.getCharByName(data.artistName)

                        if (existingChar != null) {
                            dao.delete(existingChar)
                        } else {
                            val charEntity = CharEntity(
                                artistName = data.artistName,
                                artistHref = data.artistHref,
                                url = data.url
                            )
                            dao?.insert(charEntity)
                        }
                    }.start()
                }

                itemView.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemCharsViewHolder {
        val binding = ItemCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return itemCharsViewHolder(binding)
    }

    override fun getItemCount(): Int = listChar.size

    override fun onBindViewHolder(holder: itemCharsViewHolder, position: Int) {
        holder.bind(listChar[position])
    }
}