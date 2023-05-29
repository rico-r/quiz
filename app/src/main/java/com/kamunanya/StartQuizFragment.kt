package com.kamunanya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kamunanya.databinding.FragmentStartQuizBinding

class StartQuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStartQuizBinding.inflate(inflater, container, false)
        val args = StartQuizFragmentArgs.fromBundle(requireArguments())
        val quizData = QuizData.fromJson(args.quizData)

        binding.quizTitle.text = quizData.title
        binding.deskripsi.text = quizData.desc

        findNavController().clearBackStack(0)

        binding.createQuizButton.setOnClickListener {
            findNavController()
                .navigate(StartQuizFragmentDirections
                    .actionStartQuizFragmentToQuizFragment(args.quizData))
        }
        return binding.root
    }

}