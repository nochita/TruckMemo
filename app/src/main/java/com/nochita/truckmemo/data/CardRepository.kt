package com.nochita.truckmemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nochita.truckmemo.R
import com.nochita.truckmemo.model.Card

class CardRepository {

    suspend fun getAllCards() : LiveData<List<Card>> {
            val cards = listOf(
                Card(1, 2, R.drawable.car_1),
                Card(2, 1,  R.drawable.truck_1),
                Card(3, 3, R.drawable.firetruck),
                Card(4, 4, R.drawable.car_2),
                Card(5, 5, R.drawable.truck_2),
                Card(6, 6, R.drawable.truck_3),
                Card(7, 7, R.drawable.tractor),
                Card(8, 8, R.drawable.truck_4),
                Card(9, 9, R.drawable.truck_5),
                Card(10, 10, R.drawable.truck_6)
            )

        return MutableLiveData<List<Card>>(cards)
        }
}