package com.belva.pedometar.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// ulazna točka za pristup Room db
// Pruža metode za dobivanje instanci DAO-a i osigurava stvaranje samo jedne instance baze podataka tijekom lifecyl-a apk

@Database(entities = [DailyStepsEntity::class], version = 1)
abstract class PedometerDB : RoomDatabase() {

    abstract fun dailyStepsDao(): DailyStepsDao

    companion object {
        @Volatile
        private var instance: PedometerDB? = null

        fun getInstance(context: Context): PedometerDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PedometerDB {
            return Room.databaseBuilder(
                context,
                PedometerDB::class.java,
                "pedometer_db_1112"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}