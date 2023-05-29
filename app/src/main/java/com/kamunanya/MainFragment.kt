package com.kamunanya

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.createQuizButton.setOnClickListener { startEditActivity(-1) }
        binding.startQuizButton.setOnClickListener { startQuizActivity() }
        binding.editQuizButton.setOnClickListener { startEditActivity(quizzes[adapter.selectedIndex].id) }
        binding.deleteButton.setOnClickListener { confirmDelete() }

        val recyclerView = binding.recyclerView
        adapter = QuizItemAdapter(listOf())

        adapter.setOnItemClickListener{
            binding.editQuizButton.isEnabled = true
            binding.deleteButton.isEnabled = true
            binding.startQuizButton.isEnabled = true
            selectedQuizIndex = adapter.selectedIndex
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        reloadList()
        setAppTitle(this, resources.getString(R.string.app_name))
    }

    private fun reloadList() {
        quizzes = QuizDB.getInstance(requireContext()).getAll()
        selectedQuizIndex = -1
        adapter.dataset = quizzes
        binding.recyclerView.adapter = adapter
        binding.editQuizButton.isEnabled = false
        binding.deleteButton.isEnabled = false
        binding.startQuizButton.isEnabled = false
    }

    private fun confirmDelete() {
        val quiz = quizzes[adapter.selectedIndex]
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirm_delete_title)
            .setMessage(resources.getString(R.string.confirm_delete_content, quiz.title))
            .setPositiveButton(R.string.yes) { dialog: DialogInterface, id: Int ->
                QuizDB.getInstance(requireContext()).delete(quiz)
                reloadList()
            }
            .setNegativeButton(R.string.no, null)
            .create()
            .show()
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