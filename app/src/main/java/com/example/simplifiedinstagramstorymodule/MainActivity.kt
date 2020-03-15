package com.example.simplifiedinstagramstorymodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplifiedinstagramstorymodule.ui.story.StoryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            startActivity(StoryActivity.callingIntent(this))
        }
    }
}
