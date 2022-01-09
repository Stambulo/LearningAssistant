package com.stambulo.learningassistant.view.fragments.home

import com.stambulo.learningassistant.view.model.DataItemClasses

sealed class ClassesState {
    object Idle: ClassesState()
    object Loading: ClassesState()
    data class Error(val error: String?): ClassesState()
    data class ClassesSuccess(val classes: List<DataItemClasses>): ClassesState()
}
