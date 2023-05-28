package com.kamunanya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kamunanya.databinding.FragmentEditQuestionBinding

class EditQuestionFragment : Fragment() {
    lateinit var quiz: QuizData
    var questionIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        val args = EditQuestionFragmentArgs.fromBundle(requireArguments())
        quiz = QuizData.fromJson(args.data)
        questionIndex = args.index
//        binding.saveQuestion.setOnClickListener {
//            findNavController()
//                .navigate(EditQuestionFragmentDirections
//                    .actionEditQuestionFragmentToEditFragment(getData().asJson()))
//        }

        return binding.root
    }

    fun getData(): QuizData {
        if(questionIndex == -1) {
            quiz.question.add(QuestionData("q1", "ans", listOf("al1", "alt2", "alt3")))
        }
        return quiz
    }

}