package com.kamunanya

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kamunanya.adapter.QuestionItemAdapter
import com.kamunanya.databinding.FragmentEditBinding

class EditFragment : Fragment(), QuestionItemAdapter.OnItemClickListener {
    var quizId = -1L
    lateinit var questions: MutableList<QuestionData>
    lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        val args = EditFragmentArgs.fromBundle(requireArguments())
        val quiz = QuizData.fromJson(args.data)
        quizId = quiz.id
        binding.judulKuis.setText(quiz.title)
        binding.deskripsiKuis.setText(quiz.desc)
        questions = quiz.question
        binding.createQuizButton.setOnClickListener {
            findNavController()
                .navigate(EditFragmentDirections
                    .actionEditFragmentToEditQuestionFragment(-1, getData().asJson()))
        }
        reloadQuestionList()

        setHasOptionsMenu(true)
        setAppTitle(this, resources.getString(R.string.title_edit_quiz))
        return binding.root
    }

    private fun reloadQuestionList() {
        val adapter = QuestionItemAdapter(questions)
        adapter.setOnItemClickListener(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onClickEdit(index: Int) {
        findNavController()
            .navigate(EditFragmentDirections
                .actionEditFragmentToEditQuestionFragment(index, getData().asJson()))
    }

    override fun onClickDelete(index: Int) {
        val question = questions[index]
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirm_delete_title)
            .setMessage(resources.getString(R.string.confirm_delete_content, question.question))
            .setPositiveButton(R.string.yes) { dialog: DialogInterface, id: Int ->
                questions.removeAt(index)
                reloadQuestionList()
            }
            .setNegativeButton(R.string.no, null)
            .create()
            .show()
    }

    fun getData(): QuizData {
        return QuizData(
            quizId,
            binding.judulKuis.text.toString(),
            binding.deskripsiKuis.text.toString(),
            questions)
    }

    fun save() {
        if(quizId == -1L) {
            // Create new
            QuizDB.getInstance(requireContext()).create(getData())
        } else {
            // Update existing
            QuizDB.getInstance(requireContext()).update(getData())
        }
        Toast.makeText(requireContext(), resources.getText(R.string.saved), Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    fun validate() {
        var errorMsg: String? = null
        if(binding.judulKuis.text.isEmpty()) {
            errorMsg = resources.getString(R.string.error_title_empty)
            binding.judulKuis.findFocus()
        } else if(binding.deskripsiKuis.text.isEmpty()) {
            errorMsg = resources.getString(R.string.error_desc_empty)
            binding.deskripsiKuis.findFocus()
        } else {
            save()
            return
        }
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit_quiz, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> validate()
        }
        return super.onOptionsItemSelected(item)
    }
}