package com.nochita.truckmemo.ui.lobby

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.nochita.truckmemo.R

class LobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        supportFragmentManager.commit { replace(R.id.flContent,
            LobbyFragment.newInstance()
        ) }
    }


}
