package com.kamunanya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.kamunanya.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityQuizBinding>(this, R.layout.activity_quiz)

        val navController = findNavController(R.id.quizNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.quizNavHostFragment)
        val result = navController.navigateUp()
        if(!result) {
            finish()
        }
        return result
    }
}