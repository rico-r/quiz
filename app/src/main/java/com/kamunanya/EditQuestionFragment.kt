package com.kamunanya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kamunanya.databinding.FragmentEditQuestionBinding

class EditQuestionFragment : Fragment() {
    lateinit var quiz: QuizData
    lateinit var binding: FragmentEditQuestionBinding
    var questionIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditQuestionBinding.inflate(inflater, container, false)
        val args = EditQuestionFragmentArgs.fromBundle(requireArguments())
        quiz = QuizData.fromJson(args.data)
        questionIndex = args.index
        binding.createQuizButton.setOnClickListener { validate() }
        if(questionIndex != -1) {
            val question = quiz.question[questionIndex]
            binding.isiPertanyaan.setText(question.question)
            binding.isiJawaban.setText(question.ans)
            binding.pilihan1.setText(question.alt[0])
            binding.pilihan2.setText(question.alt[1])
            binding.pilihan3.setText(question.alt[2])
        }

        setAppTitle(this, resources.getString(R.string.title_edit_question))
        return binding.root
    }

    fun getResutlingQuestion(): QuestionData {
        return QuestionData(
            binding.isiPertanyaan.text.toString(),
            binding.isiJawaban.text.toString(),
            listOf(
                binding.pilihan1.text.toString(),
                binding.pilihan2.text.toString(),
                binding.pilihan3.text.toString()
            )
        )
    }

    fun save() {
        if(questionIndex == -1) {
            quiz.question.add(getResutlingQuestion())
        } else {
            quiz.question[questionIndex] = getResutlingQuestion()
        }
        findNavController()
            .navigate(EditQuestionFragmentDirections
                .actionEditQuestionFragmentToEditFragment(quiz.asJson()))
    }

    fun validate() {
        var errorMsg = ""
        if(binding.isiPertanyaan.text.isEmpty()) {
            binding.isiPertanyaan.requestFocus()
            errorMsg = resources.getString(R.string.error_question_empty)
        } else if(binding.isiJawaban.text.isEmpty()) {
            binding.isiJawaban.requestFocus()
            errorMsg = resources.getString(R.string.error_ans_empty)
        } else if(binding.pilihan1.text.isEmpty()) {
            binding.pilihan1.requestFocus()
            errorMsg = resources.getString(R.string.error_alt1_empty)
        } else if(binding.pilihan2.text.isEmpty()) {
            binding.pilihan3.requestFocus()
            errorMsg = resources.getString(R.string.error_alt2_empty)
        } else if(binding.pilihan3.text.isEmpty()) {
            binding.pilihan3.requestFocus()
            errorMsg = resources.getString(R.string.error_alt3_empty)
        } else {
            save()
            return
        }
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
    }

}