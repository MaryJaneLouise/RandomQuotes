package com.mariejuana.randomquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mariejuana.randomquotes.ui.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment()).commit()
    }
}