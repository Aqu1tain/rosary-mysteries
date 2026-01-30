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
            Mystery(1, R.string.joyful_1_title, R.string.joyful_1_desc, R.string.joyful_1_fruit, R.string.joyful_1_ref, "LUK.1.26-38"),
            Mystery(2, R.string.joyful_2_title, R.string.joyful_2_desc, R.string.joyful_2_fruit, R.string.joyful_2_ref, "LUK.1.39-56"),
            Mystery(3, R.string.joyful_3_title, R.string.joyful_3_desc, R.string.joyful_3_fruit, R.string.joyful_3_ref, "LUK.2.1-20"),
            Mystery(4, R.string.joyful_4_title, R.string.joyful_4_desc, R.string.joyful_4_fruit, R.string.joyful_4_ref, "LUK.2.22-40"),
            Mystery(5, R.string.joyful_5_title, R.string.joyful_5_desc, R.string.joyful_5_fruit, R.string.joyful_5_ref, "LUK.2.41-52")
        )
    )

    private val sorrowful = MysterySet(
        type = MysteryType.SORROWFUL,
        titleResId = R.string.sorrowful_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.sorrowful_1_title, R.string.sorrowful_1_desc, R.string.sorrowful_1_fruit, R.string.sorrowful_1_ref, "MAT.26.36-46"),
            Mystery(2, R.string.sorrowful_2_title, R.string.sorrowful_2_desc, R.string.sorrowful_2_fruit, R.string.sorrowful_2_ref, "MAT.27.26"),
            Mystery(3, R.string.sorrowful_3_title, R.string.sorrowful_3_desc, R.string.sorrowful_3_fruit, R.string.sorrowful_3_ref, "MAT.27.27-31"),
            Mystery(4, R.string.sorrowful_4_title, R.string.sorrowful_4_desc, R.string.sorrowful_4_fruit, R.string.sorrowful_4_ref, "MRK.15.21-22"),
            Mystery(5, R.string.sorrowful_5_title, R.string.sorrowful_5_desc, R.string.sorrowful_5_fruit, R.string.sorrowful_5_ref, "LUK.23.33-46")
        )
    )

    private val glorious = MysterySet(
        type = MysteryType.GLORIOUS,
        titleResId = R.string.glorious_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.glorious_1_title, R.string.glorious_1_desc, R.string.glorious_1_fruit, R.string.glorious_1_ref, "LUK.24.1-12"),
            Mystery(2, R.string.glorious_2_title, R.string.glorious_2_desc, R.string.glorious_2_fruit, R.string.glorious_2_ref, "MRK.16.19-20"),
            Mystery(3, R.string.glorious_3_title, R.string.glorious_3_desc, R.string.glorious_3_fruit, R.string.glorious_3_ref, "ACT.2.1-13"),
            Mystery(4, R.string.glorious_4_title, R.string.glorious_4_desc, R.string.glorious_4_fruit, R.string.glorious_4_ref, "LUK.1.46-55"),
            Mystery(5, R.string.glorious_5_title, R.string.glorious_5_desc, R.string.glorious_5_fruit, R.string.glorious_5_ref, "REV.12.1-6")
        )
    )

    private val luminous = MysterySet(
        type = MysteryType.LUMINOUS,
        titleResId = R.string.luminous_mysteries,
        mysteries = listOf(
            Mystery(1, R.string.luminous_1_title, R.string.luminous_1_desc, R.string.luminous_1_fruit, R.string.luminous_1_ref, "MAT.3.13-17"),
            Mystery(2, R.string.luminous_2_title, R.string.luminous_2_desc, R.string.luminous_2_fruit, R.string.luminous_2_ref, "JHN.2.1-12"),
            Mystery(3, R.string.luminous_3_title, R.string.luminous_3_desc, R.string.luminous_3_fruit, R.string.luminous_3_ref, "MRK.1.14-15"),
            Mystery(4, R.string.luminous_4_title, R.string.luminous_4_desc, R.string.luminous_4_fruit, R.string.luminous_4_ref, "MAT.17.1-9"),
            Mystery(5, R.string.luminous_5_title, R.string.luminous_5_desc, R.string.luminous_5_fruit, R.string.luminous_5_ref, "MAT.26.26-30")
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
