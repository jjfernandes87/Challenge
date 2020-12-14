package br.com.mouzinho.marvelapp.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.mouzinho.marvelapp.R
import br.com.mouzinho.marvelapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setupUi()
    }

    private fun setupUi() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, MainFragment())
            .commit()
    }
}