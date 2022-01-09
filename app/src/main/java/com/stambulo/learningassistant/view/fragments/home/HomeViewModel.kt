package com.stambulo.learningassistant.view.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stambulo.learningassistant.repository.AssistantRepository
import com.stambulo.learningassistant.utility.DateUtility
import com.stambulo.learningassistant.view.model.DataItemClasses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    @Inject lateinit var repository: AssistantRepository
    private val dateUtility = DateUtility()
    val classesIntent = Channel<ClassesIntent>(Channel.UNLIMITED)
    val homeworkIntent = Channel<HomeworkIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<ClassesState>(ClassesState.Idle)
    val state: StateFlow<ClassesState> get() = _state
    private val _homeworkState = MutableStateFlow<HomeworkState>(HomeworkState.Idle)
    val homeworkState: StateFlow<HomeworkState> get() = _homeworkState
    var currentClass: Int = 0

    init {
        handleIntents()
        handleHomeworkIntent()
    }

    private fun handleHomeworkIntent(){
        viewModelScope.launch {
            homeworkIntent.consumeAsFlow().collect {
                when (it) {is HomeworkIntent.FetchHomework -> fetchHomework()}
            }
        }
    }

    private fun handleIntents() {
        viewModelScope.launch {
            classesIntent.consumeAsFlow().collect {
                when (it) {is ClassesIntent.FetchClasses -> fetchClasses()}
            }
        }
    }

    private fun fetchClasses() {
        currentClass = 0
        _state.value = ClassesState.Loading
        viewModelScope.launch {
            try {
                var newClassesList: ArrayList<DataItemClasses> = ArrayList()
                repository.getClasses(Date()).forEach {
                    val isActiv = dateUtility.isDatePeriod(it.startLesson, it.endLesson)
                    if (isActiv) currentClass = newClassesList.size
                    if (it.topic.isEmpty()){
                        newClassesList.add(
                            DataItemClasses.Classes(
                                it.startLesson, it.endLesson, it.subject,
                                it.teacher, it.topic, it.icon, isActiv
                            )
                        )
                    } else {
                        newClassesList.add(
                            DataItemClasses.AdditionalClasses(
                                it.startLesson, it.endLesson, it.subject,
                                it.teacher, it.topic, it.icon, isActiv
                            )
                        )
                    }
                }
                _state.value = ClassesState.ClassesSuccess(newClassesList)
            } catch (e: Exception) { ClassesState.Error(e.localizedMessage) }
        }
    }

    private fun fetchHomework(){
        _homeworkState.value = HomeworkState.Loading
        viewModelScope.launch {
            try{
                _homeworkState.value = HomeworkState.HomeworkSuccess(repository.getHomework())
            } catch (e: Exception){_homeworkState.value = HomeworkState.Error(e.localizedMessage)}
        }
    }
}
