package com.belva.pedometar.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.belva.pedometar.room_db.StepsRepository
import com.belva.pedometar.worker.StepCounterWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


//klasa sa logikom za resetiranja koraka od ponovnog pokretanja koraka u Bazi podataka i postavlja Periodic Work na uredaj

@AndroidEntryPoint
class RebootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var stepsRepository: StepsRepository


    //Metoda kad dobije Broadcast intent. Provjerava je li intent " ACTION_BOOT_COMPLETED", a ako je ,
    // resetira  steps since boot u bazi podataka i postavlja  periodic work od StepsCounterWorker.periodicWork(context).


    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        // Potrebno resetirat u Bazi podataka
        stepsRepository.updateStepsSinceBoot(0)

        StepCounterWorker.periodicWork(context)
    }
}