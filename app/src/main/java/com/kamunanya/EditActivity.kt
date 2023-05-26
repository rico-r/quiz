package com.kamunanya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.kamunanya.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEditBinding>(this, R.layout.activity_edit)

        val navController = this.findNavController(R.id.editNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.editNavHostFragment)
        val result = navController.navigateUp()
        if(!result) {
            finish()
        }
        return result
    }
}