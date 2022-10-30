

package com.example.finalsubmissionrecylerviewsederhana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalsubmissionrecylerviewsederhana.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
      val viewModel : NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "NewsApps"
        setContentView(R.layout.activity_main)
        bottom_navigation_menu.setupWithNavController(mainFragment.findNavController())
    }
}