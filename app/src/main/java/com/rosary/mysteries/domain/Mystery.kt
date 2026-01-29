package com.rosary.mysteries.domain

data class Mystery(
    val number: Int,
    val titleResId: Int,
    val descriptionResId: Int,
    val fruitResId: Int,
    val referenceResId: Int
)
