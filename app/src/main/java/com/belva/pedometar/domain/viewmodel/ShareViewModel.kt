package com.belva.pedometar.domain.viewmodel

import androidx.lifecycle.viewModelScope
import com.belva.pedometar.room_db.StepsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



// klasa   za kapsuliranje stanja povezanog s dijeljenjem inf o broju koraka u korisničkom sučelju
// Omogućuje spremanje ukupnog broja koraka danas (stepsToday) i  DailyStepsEntity (stepsAllDays) koji predstavljaju podatke o broju koraka za sve dane.

@HiltViewModel
class ShareViewModel @Inject constructor(
    stepsRepository: StepsRepository
) : StatefulViewModel<ShareUiState>(ShareUiState()) {

    init {
        viewModelScope.launch {
            stepsRepository.getAllStepsFlow().collect { stepsAllDays ->
                updateState { copy(stepsAllDays = stepsAllDays) }
            }
        }

        viewModelScope.launch {
            stepsRepository.getStepsTodayFlow().collect { stepsToday ->
                updateState { copy(stepsToday = stepsToday) }
            }
        }
    }
}