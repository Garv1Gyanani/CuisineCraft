package com.example.cuisinecraft

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cuisinecraft.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var binding: ActivitySearchBinding
    private lateinit var recipes: List<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.searchb.requestFocus()

        val db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        recipes = daoObject.getAll() ?: emptyList()
        setupRecyclerView()

        binding.backhome.setOnClickListener {
            finish()
        }

        binding.searchb.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    filter(s.toString())
                } else {
                    rvAdapter.filterList(dataList)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filter(filterText: String) {
        val filteredList = ArrayList<Recipe>()
        for (recipe in recipes) {
            if (recipe.tittle.lowercase().contains(filterText.lowercase())) {
                filteredList.add(recipe)
            }
        }
        rvAdapter.filterList(filteredList)
    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        for (recipe in recipes) {
            if (recipe.category.contains("Popular")) {
                dataList.add(recipe)
            }
        }
        rvAdapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = rvAdapter
    }
}
