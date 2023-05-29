package com.kamunanya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kamunanya.adapter.AnswerAdapter
import com.kamunanya.databinding.FragmentQuizBinding

class QuizFragment: Fragment() {
    lateinit var binding: FragmentQuizBinding
    lateinit var quizDataJson: String
    lateinit var shuffledQuizData: ShuffledQuizData
    var quesitionIndex = -1
    var correctCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        val args = QuizFragmentArgs.fromBundle(requireArguments())
        val quizData = QuizData.fromJson(args.quizData)
        quizDataJson = args.quizData
        shuffledQuizData = quizData.shuffle()
        nextQustion()

        return binding.root
    }

    fun finishQuiz() {
        findNavController()
            .navigate(QuizFragmentDirections
                .actionQuizFragmentToResultFragment(quizDataJson, correctCount))
    }

    fun nextQustion() {
        quesitionIndex++
        if(quesitionIndex == shuffledQuizData.question.size) {
            finishQuiz()
            return
        }

        val question = shuffledQuizData.question[quesitionIndex]
        val adapter = AnswerAdapter(question.answer)
        binding.createQuizButton.isEnabled = false
        binding.myTextView.text = question.question
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener {
            binding.createQuizButton.isEnabled = true
        }
        binding.createQuizButton.setOnClickListener {
            if(adapter.selectedIndex == question.correctAnswerIndex) {
                correctCount++
            }
            nextQustion()
        }
        setAppTitle(this, resources.getString(R.string.question_index, quesitionIndex + 1, shuffledQuizData.question.size))
    }
}