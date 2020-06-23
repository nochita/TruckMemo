package com.nochita.truckmemo.ui.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nochita.truckmemo.data.CardRepository

class CardsViewModel : ViewModel(){

    private val cardRepository = CardRepository()

    val cards = liveData {
        val cards = cardRepository.getAllCards()
        emitSource(cards)
    }
}
