package com.nochita.truckmemo.ui.gameplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nochita.truckmemo.R
import com.nochita.truckmemo.model.Card
import kotlinx.android.synthetic.main.card_item.view.*

class CardViewHolder  (view: View) : RecyclerView.ViewHolder(view) {


    companion object {

        const val TAG_POSITION = 1

        internal fun create(parent: ViewGroup): CardViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
            return CardViewHolder(view)
        }
    }

    internal fun bindTo(card: Card, listener: GamePlayAdapter.OnCardClicked, position: Int ) = with(itemView) {
        this.tag = position
        card_container.setBackgroundResource(if (card.isFlipped) card.imageResId else R.drawable.back_card)
        if(card.isFlipped){
            card_container.setOnClickListener{ null }
        } else {
            card_container.setOnClickListener{ view: View? ->  listener.onCardClicked( card, view?.tag as Int) }
        }
    }

}