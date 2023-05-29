package com.kamunanya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kamunanya.databinding.FragmentResultBinding

class ResultFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentResultBinding.inflate(inflater, container, false)
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        val quizData = QuizData.fromJson(args.quizData)
        binding.quizTitle.text = quizData.title
        val correctRatio = args.correctCount.toFloat() / quizData.question.size
        var stringId = when(true) {
            correctRatio == 1.0f -> R.string.quiz_result4
            correctRatio > 2/3.0f -> R.string.quiz_result3
            correctRatio > 1/3.0f -> R.string.quiz_result2
            else -> R.string.quiz_result1
        }
        val drawableId = when(true) {
            correctRatio == 1.0f -> R.drawable.result_4
            correctRatio > 2/3.0f -> R.drawable.result_3
            correctRatio > 1/3.0f -> R.drawable.result_2
            else -> R.drawable.result_1
        }
        binding.hasil.text = resources.getString(stringId, args.correctCount, quizData.question.size)
        binding.imageView.setImageResource(drawableId)
        binding.createQuizButton.setOnClickListener {
            findNavController()
                .navigate(ResultFragmentDirections
                    .actionResultFragmentToStartQuizFragment(args.quizData))
        }
        return binding.root
    }
}