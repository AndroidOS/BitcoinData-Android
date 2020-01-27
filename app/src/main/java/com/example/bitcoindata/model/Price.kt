package com.example.bitcoindata.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Price(
    val date: String?,
    val amount: Double?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
