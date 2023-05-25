package com.kamunanya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.kamunanya.adapter.QuizItemAdapter
import com.kamunanya.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var adapter: QuizItemAdapter
    lateinit var quizzes: List<QuizData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizzes = listOf(
            QuizData(0, "Judul 1", "Deskripsi 1", listOf<QuestionData>()),
            QuizData(0, "Judul 2", "Deskripsi 2", listOf<QuestionData>()),
            QuizData(0, "Judul 3", "Deskripsi 3", listOf<QuestionData>()),
        )

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.editQuizButton.isEnabled = false
        binding.startQuizButton.isEnabled = false

        binding.startQuizButton.setOnClickListener { startQuizActivity() }
        binding.editQuizButton.setOnClickListener { startEditActivity() }

        val recyclerView = binding.recyclerView
        adapter = QuizItemAdapter(quizzes)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener{
            binding.editQuizButton.isEnabled = true
            binding.startQuizButton.isEnabled = true
            Toast.makeText(context, "item ${adapter.selectedIndex} clicked", Toast.LENGTH_SHORT)
        }

        // Add divider to list item
        val dividerItemDecoration = MaterialDividerItemDecoration(
            inflater.context,
            MaterialDividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.isLastItemDecorated = false
        recyclerView.addItemDecoration(dividerItemDecoration)
        return binding.root
    }

    private fun startEditActivity() {
        val intent = Intent(requireContext(), QuizActivity::class.java)
        val data = Bundle()
        data.putLong("qid", quizzes[adapter.selectedIndex].id)
        startActivity(intent)
    }

    private fun startQuizActivity() {
        TODO("Not yet implemented")
    }

}