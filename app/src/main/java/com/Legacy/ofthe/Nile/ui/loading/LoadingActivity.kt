package com.Legacy.ofthe.Nile.ui.loading

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.Legacy.ofthe.Nile.R
import com.Legacy.ofthe.Nile.databinding.ActivityLoadingBinding
import com.Legacy.ofthe.Nile.ui.main.MainActivity

class LoadingActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoadingViewModel>()
    private val binding by lazy { ActivityLoadingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeLoading()
        observeFinishLoading()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    private fun observeLoading(){
        viewModel.progressLD.observe(this){
            binding.pbLoading.setProgress(it,true)
        }
    }
    private fun observeFinishLoading(){
        viewModel.loadingFinished.observe(this){
            val intenet = Intent(this, MainActivity::class.java)
            startActivity(intenet)
        }
    }

}