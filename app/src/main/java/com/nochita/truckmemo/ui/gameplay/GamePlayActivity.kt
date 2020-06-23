package com.nochita.truckmemo.ui.gameplay

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.nochita.truckmemo.R


class GamePlayActivity : AppCompatActivity() {

    companion object{
        const val ARG_ROWS = "ARG_ROWS"
        const val ARG_COLUMNS = "ARG_COLUMS"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)

        val bundle = intent.extras
        bundle?.let {
            val rows = bundle.getInt(ARG_ROWS)
            val columns = bundle.getInt(ARG_COLUMNS)
            supportFragmentManager.commit { replace(
                R.id.flContent,
                GamePlayFragment.newInstance(rows, columns)) }
        }

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(R.string.game_play)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
