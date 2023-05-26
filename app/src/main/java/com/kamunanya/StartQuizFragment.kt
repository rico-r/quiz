package com.kamunanya

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kamunanya.databinding.FragmentStartQuizBinding

class StartQuizFragment : Fragment() {
    lateinit var quizData: QuizData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStartQuizBinding.inflate(inflater, container, false)
        val args = StartQuizFragmentArgs.fromBundle(requireArguments())
        quizData = QuizData.fromJson(args.quizData)

        binding.quizTitle.text = quizData.title
        binding.deskripsi.text = quizData.desc

        findNavController().clearBackStack(0)

        binding.createQuizButton.setOnClickListener {
            findNavController()
                .navigate(StartQuizFragmentDirections
                    .actionStartQuizFragmentToQuizFragment(args.quizData))
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getShareQuizIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_quiz_text, quizData.title, quizData.toUri().toString()))
        return shareIntent
    }

    private fun shareQuiz() {
        startActivity(getShareQuizIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_share, menu)
        if (null == getShareQuizIntent().resolveActivity(requireActivity().packageManager)) {
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareQuiz()
        }
        return super.onOptionsItemSelected(item)
    }
}