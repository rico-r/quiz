package com.kamunanya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kamunanya.QuizData
import com.kamunanya.databinding.ItemPilihanBinding
import com.kamunanya.databinding.QuizItemBinding

class AnswerAdapter (
var dataset: List<String>
) : RecyclerView.Adapter<AnswerAdapter.ItemViewHolder>() {
    var selectedIndex: Int = -1
    lateinit var selectedItem: ItemViewHolder
    private var mClickListener: View.OnClickListener? = null

    fun setOnItemClickListener(listener: View.OnClickListener) {
        mClickListener = listener
    }

    class ItemViewHolder(private val binding: ItemPilihanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val quizTitle = binding.myTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val binding = ItemPilihanBinding.inflate(adapterLayout, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.quizTitle.text = item
        holder.root.setOnClickListener {
            if (selectedIndex != -1) {
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