package com.stambulo.learningassistant.repository

import com.stambulo.learningassistant.model.Classes
import com.stambulo.learningassistant.model.Homework
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class AssistantRepository @Inject constructor(){
    suspend fun getClasses(): List<Classes> {
        delay(400)
        return arrayListOf(
            Classes("08:00","09:00","Информатика","Учитель - Джобс","","file:///android_asset/a1.png"),
            Classes("10:00","12:00","Геометрия","Учитель - Перельман","Гипотеза Пуанкаре","file:///android_asset/a2.png"),
            Classes("12:00","14:00","Алгебра","Учитель - Стоянов","","file:///android_asset/a3.png"),
            Classes("14:00","16:00","История","Учитель - Иванов","Древний Рим","file:///android_asset/a4.png"),
            Classes("16:00","18:00","Химия","Учитель - Кюри","Двухвалентный кислород","file:///android_asset/a5.png"),
            Classes("18:00","20:00","Литература","Учитель - Тургенев","","file:///android_asset/a6.png"),
            Classes("20:00","22:00","Физика","Учитель - Полянский","Правило буравчика","file:///android_asset/a7.png"),
            Classes("22:00","23:55","География","Учитель - Конюхов","","file:///android_asset/a8.png"),
        )
    }

    suspend fun getHomework(): List<Homework> {
        delay(700)
        return arrayListOf(
            Homework("12-02-2022","География","Выучить длину экватора","file:///android_asset/a8.png"),
            Homework("01-03-2022","Химия","Скинуться на микроскоп","file:///android_asset/a5.png"),
            Homework("15-04-2022","Информатика","COVID-19 приложение","file:///android_asset/a1.png"),
            Homework("04-06-2022","Геометрия","Площадь петли Мёбиуса","file:///android_asset/a2.png"),
        )
    }
}
