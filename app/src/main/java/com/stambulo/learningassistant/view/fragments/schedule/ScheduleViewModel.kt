package com.stambulo.learningassistant.view.fragments.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stambulo.learningassistant.repository.AssistantRepository
import com.stambulo.learningassistant.utility.DateUtility
import com.stambulo.learningassistant.view.fragments.mvi.ClassesIntent
import com.stambulo.learningassistant.view.fragments.mvi.ClassesState
import com.stambulo.learningassistant.view.model.DataItemClasses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ScheduleViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var repository: AssistantRepository
    @Inject lateinit var dateUtility: DateUtility
    val classesIntent = Channel<ClassesIntent>(Channel.UNLIMITED)
    val _state = MutableStateFlow<ClassesState>(ClassesState.Idle)
    val state: StateFlow<ClassesState> get() = _state
    var currentClass: Int = 0

    init{ handleIntent() }

    private fun handleIntent(){
        viewModelScope.launch {
            classesIntent.consumeAsFlow().collect {
                when (it) {is ClassesIntent.FetchClasses -> fetchClasses()}
            }
        }
    }

    private fun fetchClasses(){
        currentClass = 0
        _state.value = ClassesState.Loading
        viewModelScope.launch {
            try {
                var newClassesList: ArrayList<DataItemClasses> = ArrayList()
                repository.getClasses().forEach {
                    val isActive = dateUtility.isDatePeriod(it.startLesson, it.endLesson)
                    if (isActive) currentClass = newClassesList.size
                    newClassesList.add(DataItemClasses.Delimiter(it.startLesson, it.endLesson, isActive))
                    if (it.topic.isEmpty()){
                        newClassesList.add(
                            DataItemClasses.Classes(
                                it.startLesson, it.endLesson, it.subject,
                                it.teacher, it.topic, it.icon, isActive
                            )
                        )
                    } else {
                        newClassesList.add(
                            DataItemClasses.AdditionalClasses(
                                it.startLesson, it.endLesson, it.subject,
                                it.teacher, it.topic, it.icon, isActive
                            )
                        )
                    }
                }
                _state.value = ClassesState.ClassesSuccess(newClassesList)
            } catch (e: Exception) { ClassesState.Error(e.localizedMessage) }
        }
    }
}
