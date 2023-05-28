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
import timber.log.Timber

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var adapter: QuizItemAdapter
    lateinit var quizzes: List<QuizData>
    var selectedQuizIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.createQuizButton.setOnClickListener { startEditActivity(-1) }
        binding.startQuizButton.setOnClickListener { startQuizActivity() }
        binding.editQuizButton.setOnClickListener { startEditActivity(quizzes[adapter.selectedIndex].id) }

        val recyclerView = binding.recyclerView
        adapter = QuizItemAdapter(listOf())
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener{
            binding.editQuizButton.isEnabled = true
            binding.startQuizButton.isEnabled = true
            selectedQuizIndex = adapter.selectedIndex
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume()")
        quizzes = QuizDB.getInstance(requireContext()).getAll()
        selectedQuizIndex = -1
        adapter.dataset = quizzes
        binding.recyclerView.adapter = adapter
        binding.editQuizButton.isEnabled = false
        binding.startQuizButton.isEnabled = false
    }

    private fun startEditActivity(quizId: Long) {
        val intent = Intent(requireContext(), EditActivity::class.java)
        intent.putExtra("qid", quizId)
        startActivity(intent)
    }

    private fun startQuizActivity() {
        val intent = Intent(requireContext(), QuizActivity::class.java)
        intent.putExtra("qid", quizzes[adapter.selectedIndex].id)
        startActivity(intent)
    }

}