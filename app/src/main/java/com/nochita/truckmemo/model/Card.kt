package com.nochita.truckmemo.model

data class Card(
    var id : Int,
    var idType: Int,
    var imageResId : Int,
    var isFlipped : Boolean = false
    ) {
    fun flipBack() {
        isFlipped = !isFlipped
    }

    fun isTheSameAs(other : Card) : Boolean {
        return idType == other.idType
    }

    fun copyWithId(newId: Int) : Card {
        return Card(newId, idType, imageResId)
    }
}
