package com.belva.pedometar.domain.viewmodel.stats

import com.belva.pedometar.room_db.DailyStepsEntity

//klasa  za predstavljanje različitih stanja/rezultata operacije -  učitavanje, uspjeh,neuspjeh za dohvaćanje iz repositorya
sealed class Response {
    object  Loading : Response()
    data class  Success (val data: List<DailyStepsEntity>) : Response()
}