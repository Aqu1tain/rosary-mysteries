package com.rosary.mysteries.domain

data class MysterySet(
    val type: MysteryType,
    val titleResId: Int,
    val mysteries: List<Mystery>
)
