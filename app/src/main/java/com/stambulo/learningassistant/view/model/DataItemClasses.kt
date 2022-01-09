package com.stambulo.learningassistant.view.model

sealed class DataItemClasses {
    data class Classes(
        val startLesson: String,
        val endLesson: String,
        val subject: String,
        val teacher: String,
        val topic: String,
        val icon: String,
        var openSkype: Boolean = false
    ): DataItemClasses()

    data class AdditionalClasses(
        val startLesson: String,
        val endLesson: String,
        val subject: String,
        val teacher: String,
        val topic: String,
        val icon: String,
        var openSkype: Boolean = false
    ): DataItemClasses()

    data class Header(
        val startLesson: String,
        val endLesson: String,
        val isActive: Boolean = false
    ): DataItemClasses()
}
