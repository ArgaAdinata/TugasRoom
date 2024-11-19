package com.example.tugasroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugasroom.database.CharRoomDatabase
import com.example.tugasroom.databinding.ActivityMainBinding
import com.example.tugasroom.model.Result
import com.example.tugasroom.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var database: CharRoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = CharRoomDatabase.getDatabase(this)

        binding.rvChar.layoutManager = GridLayoutManager(this, 2)
        loadChars()

        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    private fun loadChars() {
        val client = ApiClient.getInstance()
        val response = client.getAllChars()

        response.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        val adapter = CharAdapter(
                            listChar = result.results,
                            onClick = { char ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(char.artistHref))
                                startActivity(intent)
                            },
                            database = database
                        )
                        binding.rvChar.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Connection error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}