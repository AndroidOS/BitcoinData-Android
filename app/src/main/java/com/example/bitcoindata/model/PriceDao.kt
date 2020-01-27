package com.example.bitcoindata.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PriceDao {

        @Insert
        suspend fun insertAll(vararg prices: Price): List<Long>

        @Query("SELECT * FROM price")
        suspend fun getAllPrices(): List<Price>

        @Query("SELECT * FROM price WHERE uuid = :priceId")
        suspend fun getPrice(priceId: Int): Price

        @Query("DELETE FROM price")
        suspend fun deleteAllPrices()
}