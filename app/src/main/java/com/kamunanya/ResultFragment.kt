package com.kamunanya

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kamunanya.databinding.FragmentResultBinding

class ResultFragment: Fragment() {
    lateinit var binding: FragmentResultBinding
    lateinit var quizData: QuizData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        quizData = QuizData.fromJson(args.quizData)
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
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getShareResultIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        return shareIntent
    }

    private fun shareResult() {
        val root = requireNotNull(activity?.window?.decorView?.rootView)
        val bitmap = Bitmap.createBitmap(root.width, root.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        root.draw(canvas)

        val imgBitmapPath: String =
            MediaStore.Images.Media.insertImage(requireActivity().contentResolver, bitmap, "result", null)
        val imgBitmapUri: Uri = Uri.parse(imgBitmapPath)
        val shareIntent = getShareResultIntent()
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri)
        shareIntent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_result_content, quizData.title))
        startActivity(Intent.createChooser(shareIntent, resources.getString(R.string.share_result_title)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_share, menu)
        if (null == getShareResultIntent().resolveActivity(requireActivity().packageManager)) {
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> binding.root.postDelayed({
                binding.root.run { shareResult() }
            }, 200)
        }
        return super.onOptionsItemSelected(item)
    }
}