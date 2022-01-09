package com.stambulo.learningassistant.model

data class Classes(
    val startLesson: String,
    val endLesson: String,
    val subject: String,
    val teacher: String,
    val topic: String,
    val icon: String,
    var openSkype: Boolean = false
)
