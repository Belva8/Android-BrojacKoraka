package com.belva.pedometar.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey


// definira strukturu tablice u SQLite bazi podataka.
// Ima tri stupca: epochDay(PrimaryKey), steps i timeStamp
// Instance ove klase predstavljaju pojedinačne zapise u tablici, koristi Room data base za operacije u tablici


@Entity
data class DailyStepsEntity(
    @PrimaryKey
    val epochDay: Long,
    val steps: Long = Long.MIN_VALUE,
    val timeStamp: String = ""
)
