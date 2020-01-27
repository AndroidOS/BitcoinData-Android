package com.example.bitcoindata.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Price::class), version = 1)
abstract class PriceDatabase: RoomDatabase() {
    abstract fun priceDao(): PriceDao

    companion object{
        @Volatile private var instance: PriceDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PriceDatabase::class.java,
            "pricedatabase"
        ).build()
    }
}