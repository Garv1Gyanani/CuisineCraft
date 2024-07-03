package com.example.cuisinecraft

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cuisinecraft.databinding.ActivityHomeBinding

class home : AppCompatActivity() {
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the binding correctly
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setuprecyclerview()

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.imageViewR.setOnClickListener {
            val myintent = Intent(this@home, CategoryActivity::class.java)
            myintent.putExtra("TITTLE", "Salad")
            myintent.putExtra("CATEGORY", "Salad")
            startActivity(myintent)
        }

        binding.imageViewRd.setOnClickListener {
            val myintent = Intent(this@home, CategoryActivity::class.java)
            myintent.putExtra("TITTLE", "Desserts")
            myintent.putExtra("CATEGORY", "Desserts")
            startActivity(myintent)
        }

        binding.imageViewMd.setOnClickListener {
            val myintent = Intent(this@home, CategoryActivity::class.java)
            myintent.putExtra("TITTLE", "Drinks")
            myintent.putExtra("CATEGORY", "Drinks")
            startActivity(myintent)
        }

        binding.imageViewFd.setOnClickListener {
            val myintent = Intent(this@home, CategoryActivity::class.java)
            myintent.putExtra("TITTLE", "Main Dish")
            myintent.putExtra("CATEGORY", "Dish")
            startActivity(myintent)
        }
    }

    private fun setuprecyclerview() {
        dataList = ArrayList()
        binding.recyc.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val db = Room.databaseBuilder(
            this@home,
            AppDatabase::class.java, "db_name"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        val recipe = daoObject.getAll()

        for (i in recipe!!.indices) {
            if (recipe[i]!!.category.contains("Popular")) {
                dataList.add(recipe[i]!!)
            }
        }

        rvAdapter = PopularAdapter(dataList, this)
        binding.recyc.adapter = rvAdapter
    }
}
