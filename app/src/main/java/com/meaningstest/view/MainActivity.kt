package com.meaningstest.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.meaningstest.R
import com.meaningstest.databinding.ActivityMainBinding
import com.meaningstest.databinding.MeaningsItemBinding
import com.meaningstest.utils.DataHandler
import com.meaningstest.viewmodel.MeaningsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}