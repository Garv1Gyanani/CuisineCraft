package com.example.cuisinecraft

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cuisinecraft.databinding.SearchRvBinding

class SearchAdapter(private var dataList: ArrayList<Recipe>, private val context: Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SearchRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<Recipe>) {
        dataList = filteredList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]

        Glide.with(context).load(recipe.img).into(holder.binding.searchimage)
        holder.binding.textView7txt.text = recipe.tittle

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
    }
}
