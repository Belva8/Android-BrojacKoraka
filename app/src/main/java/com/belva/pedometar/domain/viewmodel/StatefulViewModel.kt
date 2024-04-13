package com.belva.pedometar.domain.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// klasa ViewModel služi kao osnovna klasa za druge klase ViewModel u apk
open class StatefulViewModel<T>(
    initState: T
) : ViewModel() {

    //Unutar funkcije, _uiState.update(newState): Poziva funkciju ažuriranja na promjenjivom tijeku stanja _uiState,
    // prosljeđujući lambda funkciju newState
    // Ovo ažurira tijek stanja novim stanjem generiranim primjenom lambda funkcije newState na trenutno stanje

    private val _uiState = MutableStateFlow(initState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    // funkcija updateState koja  ažurira stanja korisničkog sučelja.
    protected fun updateState(newState: T.() -> T) {
        _uiState.update(newState) //Potrebna je lambda funkcija newState koja modificira trenutno stanje i vraća novo stanje.
    }
}