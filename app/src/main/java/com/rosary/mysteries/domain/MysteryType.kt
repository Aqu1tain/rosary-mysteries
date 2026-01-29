package com.rosary.mysteries.domain

import java.time.DayOfWeek

enum class MysteryType {
    JOYFUL,
    SORROWFUL,
    GLORIOUS,
    LUMINOUS;

    companion object {
        fun forDay(day: DayOfWeek): MysteryType = when (day) {
            DayOfWeek.MONDAY, DayOfWeek.SATURDAY -> JOYFUL
            DayOfWeek.TUESDAY, DayOfWeek.FRIDAY -> SORROWFUL
            DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY -> GLORIOUS
            DayOfWeek.THURSDAY -> LUMINOUS
        }

        fun today(): MysteryType = forDay(DayOfWeek.from(java.time.LocalDate.now()))
    }
}
