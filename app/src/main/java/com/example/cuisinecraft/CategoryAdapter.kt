package com.example.cuisinecraft

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cuisinecraft.databinding.CategoryRvBinding

class CategoryAdapter(private val dataList: ArrayList<Recipe>, private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val recipe = dataList[position]
            Glide.with(context).load(recipe.img).into(img)
            textViewp.text = recipe.tittle

            val temp = recipe.ing.split("\n").dropLastWhile { it.isEmpty() }.toTypedArray()
            if (temp.isNotEmpty()) {
                timee.text = temp[0]
            }

            next.setOnClickListener {
                val intent = Intent(context, RecipeActivity::class.java).apply {
                    putExtra("img", recipe.img)
                    putExtra("tittle", recipe.tittle)
                    putExtra("des", recipe.des)
                    putExtra("ing", recipe.ing)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        }
    }
}