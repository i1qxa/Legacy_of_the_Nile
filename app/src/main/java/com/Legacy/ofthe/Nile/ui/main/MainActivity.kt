package com.Legacy.ofthe.Nile.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.ui.exit.ExitFragment
import com.Legacy.ofthe.Nile.ui.main.start.StartGameFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.mainConteiner) is StartGameFragment) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.mainConteiner, ExitFragment())
                addToBackStack(null)
                commit()
            }
        } else {
            super.onBackPressed()
        }
    }
}