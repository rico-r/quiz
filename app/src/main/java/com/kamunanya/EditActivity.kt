package com.kamunanya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kamunanya.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    lateinit var conf: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEditBinding>(this, R.layout.activity_edit)
        val navController = this.findNavController(R.id.editNavHostFragment)
        val qid = intent.getLongExtra("qid", -1L)
        var quiz = if(qid == -1L)
            QuizData(-1L, "", "", mutableListOf())
            else QuizDB.getInstance(this).get(qid)
        val args = EditFragmentArgs.Builder(quiz.asJson()).build()

        conf = AppBarConfiguration.Builder()
            .setFallbackOnNavigateUpListener {
                finish()
                true
            }.build()

        navController.setGraph(R.navigation.edit_navigation, args.toBundle())
        NavigationUI.setupActionBarWithNavController(this, navController, conf)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.editNavHostFragment)
        return NavigationUI.navigateUp(navController, conf)
    }
}