package com.belva.pedometar.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {

    // Današnji Epoch Day, dan 0 je 1970/01/01
    fun getToday(): Long = LocalDate.now().toEpochDay() //koristi LocalDate.now() da  dobije danasnji datum i
    //  toEpochDay() da ih pretvori u broj dana od  epoch.

    fun formattedCurrentTime(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss, yy-MM-dd"))

    fun timeOfClock(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("a h:mm:ss").withLocale(Locale.ENGLISH))

    fun dateOfClock(): String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM, yyyy").withLocale(Locale.ENGLISH))

}