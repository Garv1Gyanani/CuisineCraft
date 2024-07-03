package com.example.cuisinecraft

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cuisinecraft.databinding.ActivityCategaryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var binding: ActivityCategaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the binding correctly
        binding = ActivityCategaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title of the Activity
        title = intent.getStringExtra("TITTLE")

        setuprecyclerview()
        binding.imageView3.setOnClickListener {
            finish()
        }
    }

    private fun setuprecyclerview() {
        dataList = ArrayList()
        binding.catrv.layoutManager = LinearLayoutManager(this)

        val db = Room.databaseBuilder(
            this@CategoryActivity,
            AppDatabase::class.java, "db_name"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        val recipe = daoObject.getAll()

        for (i in recipe!!.indices) {
            if (recipe[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)) {
                dataList.add(recipe[i]!!)
            }
        }

        rvAdapter = CategoryAdapter(dataList, this)
        binding.catrv.adapter = rvAdapter
    }
}
