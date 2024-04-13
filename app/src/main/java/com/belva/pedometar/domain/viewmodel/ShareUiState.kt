package com.belva.pedometar.domain.viewmodel

import com.belva.pedometar.room_db.DailyStepsEntity

//Klasa  predstavlja stanje korisničkog sučelja u vezi s dijeljenjem informacija o broju koraka.

data class ShareUiState(
    val stepsToday: Long = 0,
    val stepsAllDays: List<DailyStepsEntity> = emptyList()
)
