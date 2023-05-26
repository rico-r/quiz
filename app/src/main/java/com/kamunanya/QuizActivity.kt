package com.kamunanya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kamunanya.databinding.ActivityQuizBinding
import timber.log.Timber

class QuizActivity : AppCompatActivity() {
    lateinit var conf: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityQuizBinding>(this, R.layout.activity_quiz)
        val navController = this.findNavController(R.id.quizNavHostFragment)
        var quiz: QuizData? = null
        if(intent.data != null) {
            quiz = QuizData.fromUri(requireNotNull(intent.data))
        } else {
            val qid = intent.getLongExtra("qid", -1L)
            var quiz = if (qid == -1L)
                QuizData(-1L, "", "", mutableListOf())
            else QuizDB.getInstance(this).get(qid)
        }
        val args = StartQuizFragmentArgs.Builder(requireNotNull(quiz).asJson()).build()

        conf = AppBarConfiguration.Builder()
            .setFallbackOnNavigateUpListener {
                finish()
                true
            }.build()

        navController.setGraph(R.navigation.quiz_navigation, args.toBundle())
        NavigationUI.setupActionBarWithNavController(this, navController, conf)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.quizNavHostFragment)
        return NavigationUI.navigateUp(navController, conf)
    }
}