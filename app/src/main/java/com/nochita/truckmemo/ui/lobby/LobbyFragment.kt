package com.nochita.truckmemo.ui.lobby


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nochita.truckmemo.R
import com.nochita.truckmemo.ui.gameplay.GamePlayActivity
import kotlinx.android.synthetic.main.fragment_lobby.*

/**
 * A simple [Fragment] subclass.
 */
class LobbyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    companion object{
        fun newInstance() : LobbyFragment {
            return LobbyFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        grid1.setOnClickListener{ startGamePlay(2,3) }
        grid2.setOnClickListener{ startGamePlay(2,4) }
        grid3.setOnClickListener{ startGamePlay(2,5) }
        grid4.setOnClickListener{ startGamePlay(3,4) }

    }

    private fun startGamePlay(rows: Int, columns: Int){
        val intent = Intent(activity, GamePlayActivity::class.java)
        intent.putExtra(GamePlayActivity.ARG_ROWS, rows)
        intent.putExtra(GamePlayActivity.ARG_COLUMNS, columns)
        startActivity(intent)
    }
}
