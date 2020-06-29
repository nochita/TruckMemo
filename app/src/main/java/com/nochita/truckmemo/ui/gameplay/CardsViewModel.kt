package com.nochita.truckmemo.ui.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nochita.truckmemo.data.CardRepository
import com.nochita.truckmemo.model.Card

class CardsViewModel : ViewModel(){

    private val cardRepository = CardRepository()

    val cards = liveData {
        val cards = cardRepository.getAllCards()
        emitSource(cards)
    }

    suspend fun getNeededCards(quantity : Int) : List<Card> {

        val cards = cardRepository.getAllCards().value
        cards?.let {

            val cardsNeeded = cards.shuffled().subList(0, quantity).toMutableList()
            cardsNeeded.addAll(cardsNeeded.map { original -> original.copyWithId(original.id + quantity) })

            // mix cards
            cardsNeeded.shuffle()

            return cardsNeeded
        }
        return emptyList()
    }
}
