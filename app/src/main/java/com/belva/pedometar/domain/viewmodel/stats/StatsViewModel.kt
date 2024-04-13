package com.belva.pedometar.domain.viewmodel.stats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belva.pedometar.room_db.StepsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


//klasa    za dohvaćanje podataka o dnevnom broju koraka iz repozitorija i ažuriranje stanja korisničkog sučelja u skladu s tim.
//Svojstvo responseState predstavlja trenutno stanje odgovora,  učitavanje/uspjeh i sl
//ViewModel odgovoran je za upravljanje stanjem za prikaz statistike u UI( dnevni broj koraka)
@HiltViewModel
class StatsViewModel @Inject constructor(
private val stepsRepository: StepsRepository

): ViewModel()
{

    // responseState predstavlja trenutno stanje odgovora. Inicijaliziran je stanjem učitavanja  iz klase Response.
    // mutableStateOf je funkcija Compose koja se koristi za stvaranje objekta promjenjivog stanja.
var responseState by mutableStateOf<Response>(Response.Loading)
    private set

    init {
        viewModelScope.launch {
            stepsRepository.getAllStepsFlow().distinctUntilChanged().collect{

                stepsAllDays ->
                responseState = Response.Success(stepsAllDays)

            }
        }
    }


}