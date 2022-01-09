package com.stambulo.learningassistant.view.fragments.home

import com.stambulo.learningassistant.model.Homework

sealed class HomeworkState {
    object Idle: HomeworkState()
    object Loading: HomeworkState()
    data class Error(val error: String?): HomeworkState()
    data class HomeworkSuccess(val homework: List<Homework>): HomeworkState()
}
