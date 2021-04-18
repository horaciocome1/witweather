package io.github.horaciocome1.witweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}