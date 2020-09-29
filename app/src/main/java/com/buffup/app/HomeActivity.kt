package com.buffup.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.buffup.app.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
