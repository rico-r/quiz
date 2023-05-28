package com.kamunanya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kamunanya.QuizData
import com.kamunanya.R
import com.kamunanya.databinding.QuizItemBinding

class QuizItemAdapter (
    var dataset: List<QuizData>
) : RecyclerView.Adapter<QuizItemAdapter.ItemViewHolder>() {
    var selectedIndex: Int = -1
    lateinit var selectedItem: ItemViewHolder
    private var mClickListener: View.OnClickListener? = null

    fun setOnItemClickListener(listener: View.OnClickListener) {
        mClickListener = listener
    }

    class ItemViewHolder(private val binding: QuizItemBinding): RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val quizTitle = binding.quizTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val binding = QuizItemBinding.inflate(adapterLayout, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.quizTitle.text = item.title
        holder.root.setOnClickListener{
            if(selectedIndex != -1) {
                selectedItem.root.isSelected = false
            }
            it.isSelected = true
            selectedIndex = position
            selectedItem = holder
            mClickListener?.onClick(it)
        }
    }

    override fun getItemCount() = dataset.size
}