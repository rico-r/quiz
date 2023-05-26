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
    var selectedQuizIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = QuizDB.getInstance(requireContext())
        quizzes = db.getAll()

        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.editQuizButton.isEnabled = false
        binding.startQuizButton.isEnabled = false

        binding.createQuizButton.setOnClickListener { startEditActivity(-1) }
        binding.startQuizButton.setOnClickListener { startQuizActivity() }
        binding.editQuizButton.setOnClickListener { startEditActivity(quizzes[adapter.selectedIndex].id) }

        val recyclerView = binding.recyclerView
        adapter = QuizItemAdapter(quizzes)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener{
            binding.editQuizButton.isEnabled = true
            binding.startQuizButton.isEnabled = true
            selectedQuizIndex = adapter.selectedIndex
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

    private fun startEditActivity(quizId: Long) {
        val intent = Intent(requireContext(), EditActivity::class.java)
        val data = Bundle()
        data.putLong("qid", quizId)
        startActivity(intent, data)
    }

    private fun startQuizActivity() {
        val intent = Intent(requireContext(), QuizActivity::class.java)
        val data = Bundle()
        data.putLong("qid", quizzes[adapter.selectedIndex].id)
        startActivity(intent, data)
    }

}