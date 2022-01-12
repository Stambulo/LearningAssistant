package com.stambulo.learningassistant.view.fragments.mvi

sealed class ClassesIntent {
    object FetchClasses: ClassesIntent()
}
