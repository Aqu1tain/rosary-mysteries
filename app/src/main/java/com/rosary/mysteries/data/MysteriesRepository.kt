package com.rosary.mysteries.data

import com.rosary.mysteries.R
import com.rosary.mysteries.domain.Mystery
import com.rosary.mysteries.domain.MysterySet
import com.rosary.mysteries.domain.MysteryType

object MysteriesRepository {

    private val joyful = MysterySet(
        type = MysteryType.JOYFUL,
        titleResId = R.string.joyful_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.joyful_1_title, R.string.joyful_1_desc, R.string.joyful_1_fruit),
            Mystery(2, R.string.joyful_2_title, R.string.joyful_2_desc, R.string.joyful_2_fruit),
            Mystery(3, R.string.joyful_3_title, R.string.joyful_3_desc, R.string.joyful_3_fruit),
            Mystery(4, R.string.joyful_4_title, R.string.joyful_4_desc, R.string.joyful_4_fruit),
            Mystery(5, R.string.joyful_5_title, R.string.joyful_5_desc, R.string.joyful_5_fruit)
        )
    )

    private val sorrowful = MysterySet(
        type = MysteryType.SORROWFUL,
        titleResId = R.string.sorrowful_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.sorrowful_1_title, R.string.sorrowful_1_desc, R.string.sorrowful_1_fruit),
            Mystery(2, R.string.sorrowful_2_title, R.string.sorrowful_2_desc, R.string.sorrowful_2_fruit),
            Mystery(3, R.string.sorrowful_3_title, R.string.sorrowful_3_desc, R.string.sorrowful_3_fruit),
            Mystery(4, R.string.sorrowful_4_title, R.string.sorrowful_4_desc, R.string.sorrowful_4_fruit),
            Mystery(5, R.string.sorrowful_5_title, R.string.sorrowful_5_desc, R.string.sorrowful_5_fruit)
        )
    )

    private val glorious = MysterySet(
        type = MysteryType.GLORIOUS,
        titleResId = R.string.glorious_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.glorious_1_title, R.string.glorious_1_desc, R.string.glorious_1_fruit),
            Mystery(2, R.string.glorious_2_title, R.string.glorious_2_desc, R.string.glorious_2_fruit),
            Mystery(3, R.string.glorious_3_title, R.string.glorious_3_desc, R.string.glorious_3_fruit),
            Mystery(4, R.string.glorious_4_title, R.string.glorious_4_desc, R.string.glorious_4_fruit),
            Mystery(5, R.string.glorious_5_title, R.string.glorious_5_desc, R.string.glorious_5_fruit)
        )
    )

    private val luminous = MysterySet(
        type = MysteryType.LUMINOUS,
        titleResId = R.string.luminous_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.luminous_1_title, R.string.luminous_1_desc, R.string.luminous_1_fruit),
            Mystery(2, R.string.luminous_2_title, R.string.luminous_2_desc, R.string.luminous_2_fruit),
            Mystery(3, R.string.luminous_3_title, R.string.luminous_3_desc, R.string.luminous_3_fruit),
            Mystery(4, R.string.luminous_4_title, R.string.luminous_4_desc, R.string.luminous_4_fruit),
            Mystery(5, R.string.luminous_5_title, R.string.luminous_5_desc, R.string.luminous_5_fruit)
        )
    )

    fun getAll(): List<MysterySet> = listOf(joyful, sorrowful, glorious, luminous)

    fun getByType(type: MysteryType): MysterySet = when (type) {
        MysteryType.JOYFUL -> joyful
        MysteryType.SORROWFUL -> sorrowful
        MysteryType.GLORIOUS -> glorious
        MysteryType.LUMINOUS -> luminous
    }

    fun getToday(): MysterySet = getByType(MysteryType.today())
}
