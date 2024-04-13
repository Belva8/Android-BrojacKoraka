package com.belva.pedometar.room_db

import com.belva.pedometar.common.DateUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.max



//logika za pristup i ažuriranje podataka o broju koraka u Room-u.  pruža čisto sučelje za interakciju s podacima o broju koraka.

class StepsRepository @Inject constructor(
    db: PedometerDB
){
    private val dailyStepsDao = db.dailyStepsDao()

    fun getAllStepsFlow(): Flow<List<DailyStepsEntity>> = dailyStepsDao.getAllStepsFlow()

    fun getStepsTodayFlow(): Flow<Long> {
        val today = DateUtil.getToday()
        return dailyStepsDao.getStepsFlow(today).map { it ?: 0 }
    }

    fun updateStepsSinceBoot(stepsSinceBoot: Long): Long {
        val today = DateUtil.getToday()
        val todaySteps = dailyStepsDao.getSteps(today)
        val previousStepsSinceBoot = getSteps(-1)
        // Potrebno da je barem 0
        val stepsDiff = max(stepsSinceBoot - previousStepsSinceBoot, 0)

        if (todaySteps == null) {
            addToLastEntry(stepsDiff)
            insertTodayEntry(today)
        } else {
            addToLastEntry(stepsDiff)
        }

        val newStepsSinceBoot = DailyStepsEntity(epochDay = -1, steps = stepsSinceBoot)
        dailyStepsDao.insert(newStepsSinceBoot)

        return getSteps(today)
    }

    private fun insertTodayEntry(today: Long) {
        if (dailyStepsDao.getSteps(today) == null) {
            val todaySteps = DailyStepsEntity(epochDay = today, steps = 0, timeStamp = DateUtil.formattedCurrentTime())
            dailyStepsDao.insert(todaySteps)
        }
    }

    private fun addToLastEntry(stepsDiff: Long) {
        dailyStepsDao.addToLastEntry(stepsDiff, DateUtil.formattedCurrentTime())
    }

    private fun getSteps(day: Long): Long {
        return dailyStepsDao.getSteps(day) ?: 0
    }

}