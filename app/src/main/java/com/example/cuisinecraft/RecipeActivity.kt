package com.example.cuisinecraft

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cuisinecraft.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private var imgCrop = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val img = intent.getStringExtra("img")
        val title = intent.getStringExtra("tittle")
        val ingredients = intent.getStringExtra("ing")
        val description = intent.getStringExtra("des")

        Glide.with(this).load(img).into(binding.imagepizza)

        binding.itmtittle.text = title
        binding.stepsdata.text = description

        val ing = ingredients?.split("\n")?.dropLastWhile { it.isEmpty() }?.toTypedArray() ?: emptyArray()
        if (ing.isNotEmpty()) {
            binding.showstime.text = ing[0]
            for (i in 1 until ing.size) {
                binding.ingredientdata.append("\n🍟 ${ing[i]}")
            }
        }

        // Set default view
        binding.ingscroll.visibility = View.VISIBLE
        binding.stepscroll.visibility = View.GONE
        binding.ingredient.setBackgroundResource(R.drawable.btn_ing)
        binding.ingredient.setTextColor(getColor(R.color.white))
        binding.steps.background = null
        binding.steps.setTextColor(getColor(R.color.black))

        binding.steps.setOnClickListener {
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ingredient.setTextColor(getColor(R.color.black))
            binding.ingredient.background = null
            binding.stepscroll.visibility = View.VISIBLE
            binding.ingscroll.visibility = View.GONE
        }

        binding.ingredient.setOnClickListener {
            binding.ingredient.setBackgroundResource(R.drawable.btn_ing)
            binding.ingredient.setTextColor(getColor(R.color.white))
            binding.steps.setTextColor(getColor(R.color.black))
            binding.steps.background = null
            binding.ingscroll.visibility = View.VISIBLE
            binding.stepscroll.visibility = View.GONE
        }

        binding.fittoscreen.setOnClickListener {
            binding.imagepizza.scaleType = if (imgCrop) {
                ImageView.ScaleType.FIT_CENTER
            } else {
                ImageView.ScaleType.CENTER_CROP
            }
            binding.grade.visibility = View.GONE
            imgCrop = !imgCrop
        }

        binding.backbbtn.setOnClickListener {
            finish()
        }
    }
}
