package com.example.simplifiedinstagramstorymodule.ui.story

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplifiedinstagramstorymodule.R
import com.example.simplifiedinstagramstorymodule.core.platform.BaseActivity

class StoryActivity : BaseActivity() {

    override fun fragment() = StoryFragment()

    companion object {
        fun callingIntent(context: Context) = Intent(context, StoryActivity::class.java)
    }

}
