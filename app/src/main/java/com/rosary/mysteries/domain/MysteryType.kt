package com.rosary.mysteries.domain

import java.time.DayOfWeek

enum class MysteryType {
    JOYFUL,
    SORROWFUL,
    GLORIOUS,
    LUMINOUS;

    companion object {
        fun forDay(day: DayOfWeek, luminousEnabled: Boolean = true): MysteryType = when (day) {
            DayOfWeek.MONDAY, DayOfWeek.SATURDAY -> JOYFUL
            DayOfWeek.TUESDAY, DayOfWeek.FRIDAY -> SORROWFUL
            DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY -> GLORIOUS
            DayOfWeek.THURSDAY -> if (luminousEnabled) LUMINOUS else JOYFUL
        }

        fun today(luminousEnabled: Boolean = true): MysteryType =
            forDay(DayOfWeek.from(java.time.LocalDate.now()), luminousEnabled)
    }
}
