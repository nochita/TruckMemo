package com.nochita.truckmemo.ui.gameplay

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nochita.truckmemo.model.Card

class GamePlayAdapter internal constructor(
    private val listener: OnCardClicked,
    private val cards: List<Card>
) : RecyclerView.Adapter<CardViewHolder>() {

    interface OnCardClicked {
        fun onCardClicked(card: Card, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindTo(cards[position], listener, position)
    }

    fun getItemAt(position: Int) : Card {
        return cards.get(position)
    }

}
