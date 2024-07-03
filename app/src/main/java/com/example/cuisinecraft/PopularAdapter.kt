package com.example.cuisinecraft

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cuisinecraft.databinding.PopulerRvItemBinding

class PopularAdapter(private val dataList: ArrayList<Recipe>, private val context: Context) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PopulerRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopulerRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]

        holder.binding.populerTxt.text = recipe.tittle

        val time = recipe.ing.split("\n").dropLastWhile { it.isEmpty() }.toTypedArray()
        if (time.isNotEmpty()) {
            holder.binding.popularTime.text = time[0]
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java).apply {
                putExtra("img", recipe.img)
                putExtra("tittle", recipe.tittle)
                putExtra("des", recipe.des)
                putExtra("ing", recipe.ing)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }

        Glide.with(context).load(recipe.img).into(holder.binding.populerImg)
    }
}
