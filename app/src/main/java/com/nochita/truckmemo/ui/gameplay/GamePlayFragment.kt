package com.nochita.truckmemo.ui.gameplay


import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nochita.truckmemo.R
import com.nochita.truckmemo.model.Card
import kotlinx.android.synthetic.main.fragment_game_play.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ROWS = "ARG_ROWS"
private const val ARG_COLUMNS = "ARG_COLUMNS"

/**
 * A simple [Fragment] subclass.
 * Use the [GamePlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GamePlayFragment : Fragment(), GamePlayAdapter.OnCardClicked {
    private var rows: Int = 3 //default value
    private var columns: Int = 2 //default value

    private lateinit var adapter : GamePlayAdapter

    private lateinit var viewModel: CardsViewModel
    private lateinit var cards : List<Card>
    private var pairOfCardsToFinish : Int = 10

    private var shownCardPosition : Int? = null
    private var selectedCardPosition : Int? = null

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param rows number of rows.
         * @param columns number of columns.
         * @return A new instance of fragment GamePlayFragment.
         */
        @JvmStatic
        fun newInstance(rows: Int, columns: Int) =
            GamePlayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ROWS, rows)
                    putInt(ARG_COLUMNS, columns)
                }
            }

        private const val TAG = "memo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rows = it.getInt(ARG_ROWS)
            columns = it.getInt(ARG_COLUMNS)
        }

        viewModel = ViewModelProvider(this).get(CardsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_game_play, container, false)
    }

    private fun populateBoard(cards : List<Card>) {
        pairOfCardsToFinish = cards.size / 2
        adapter = GamePlayAdapter(this, cards)
        recycleViewGamePlay.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareUI()
    }

    fun loadCards() = CoroutineScope(Main).launch {
        shimmerViewContainer?.startShimmer()
        Log.d(TAG, "Getting ${rows * columns / 2} cards")
        val result = withContext(IO) { // background thread
            viewModel.getNeededCards(rows * columns / 2)
        }
        populateBoard(result) // ui thread
        shimmerViewContainer.hideShimmer()
    }

    private fun prepareUI() {
        recycleViewGamePlay.layoutManager = GridLayoutManager(activity, columns)
        loadCards()

        game_animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                enableAnimation(true)
            }

            override fun onAnimationEnd(animation: Animator) {
                continueAfterAnimation()
                enableAnimation(false)
            }

            override fun onAnimationCancel(animation: Animator) { }

            override fun onAnimationRepeat(animation: Animator) { }
        })
    }

    private fun enableAnimation(isEnable : Boolean) {
        animation_container.isVisible = isEnable
        game_animation.isVisible = isEnable
        view_disable_layout.isVisible = isEnable //this is to disable clicking on the recycle view
    }

    override fun onCardClicked(card: Card, position: Int) {
        if(shownCardPosition == null){
            shownCardPosition = position
            fipCard(position)
        } else {
            this.selectedCardPosition = position
            fipCard(position)

            if(compareCards(shownCardPosition!!, selectedCardPosition!!)){
                match()
            } else {
                showFailedAnimation()
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun compareCards(position1 : Int, position2 : Int) : Boolean {
        val card1 = adapter.getItemAt(position1)
        val card2 = adapter.getItemAt(position2)
        return card1.isTheSameAs(card2)
    }

    private fun fipCard(position: Int){
        adapter.getItemAt(position!!).flipBack()
        adapter.notifyItemChanged(position!!)
    }

    private fun continueAfterAnimation() {
        game_animation.isVisible = false
        if(!compareCards(shownCardPosition!!, selectedCardPosition!!)){
            fipCard(shownCardPosition!!)
            shownCardPosition = null

            fipCard(selectedCardPosition!!)
            selectedCardPosition = null
        } else {
            selectedCardPosition = null
            shownCardPosition = null
        }
    }

    private fun match() {
        pairOfCardsToFinish--
        if(pairOfCardsToFinish == 0) {
            showWinGameAnimation()
        } else {
            showMatchAnimation()
        }
    }

    private fun showFailedAnimation() {
        game_animation.setAnimation("no-match-animation.json")
        showGameAnimation()
    }

    private fun showMatchAnimation() {
        game_animation.setAnimation("winking-emoji.json")
        showGameAnimation()
    }

    private fun showGameAnimation(){
        enableAnimation(true)
        game_animation.playAnimation()
    }

    private fun showWinGameAnimation(){
        enableAnimation(true)
        game_animation.isVisible = false
        confetti_animation.playAnimation()
    }

}