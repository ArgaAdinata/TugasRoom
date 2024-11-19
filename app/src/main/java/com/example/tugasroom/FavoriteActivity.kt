package com.example.tugasroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasroom.database.CharRoomDatabase
import com.example.tugasroom.databinding.ActivityFavoriteBinding
import com.example.tugasroom.model.Chars

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private var database: CharRoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        database = CharRoomDatabase.getDatabase(this)

        binding.rvFavorite.layoutManager = GridLayoutManager(this, 2)

        database?.charDao()?.getAllChars()?.observe(this) { charEntities ->
            val favoriteChars = charEntities.map { entity ->
                Chars(
                    artistName = entity.artistName,
                    artistHref = entity.artistHref,
                    url = entity.url
                )
            }

            val adapter = CharAdapter(
                listChar = favoriteChars,
                onClick = { char ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(char.artistHref))
                    startActivity(intent)
                },
                database = database
            )
            binding.rvFavorite.adapter = adapter
        }
    }
}
