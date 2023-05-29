package com.kamunanya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kamunanya.QuestionData
import com.kamunanya.databinding.ItemSoalBinding

class QuestionItemAdapter(
    var dataset: List<QuestionData>
) : RecyclerView.Adapter<QuestionItemAdapter.ItemViewHolder>() {
    private var mClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mClickListener = listener
    }

    interface OnItemClickListener {
        fun onClickEdit(index: Int)
    }

    class ItemViewHolder(private val binding: ItemSoalBinding): RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val question = binding.myTextView
        val editButton = binding.myButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
        val binding = ItemSoalBinding.inflate(adapterLayout, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.question.text = item.question
        holder.editButton.setOnClickListener{
            mClickListener?.onClickEdit(position)
        }
    }

    override fun getItemCount() = dataset.size
}