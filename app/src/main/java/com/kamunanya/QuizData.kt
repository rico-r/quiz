package com.kamunanya

import com.google.gson.Gson

data class QuizData(var id: Long=0, var title: String, var desc: String, var question: MutableList<QuestionData>) {

    fun shuffle(): ShuffledQuizData {
        val avail = question.indices.toMutableList()
        val questionList = MutableList(question.size) {
            ShuffledQuestionData("", -1,  listOf())
        }
        for(q in question) {
            val selected = avail.indices.random()
            val selectedValue = avail.removeAt(selected)
            questionList[selectedValue] = question[selectedValue].shuffle()
        }
        return ShuffledQuizData(title, desc, questionList)
    }

    fun asJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): QuizData {
            return Gson().fromJson(json, QuizData::class.java)
        }
    }
}

data class ShuffledQuizData(
    val title: String,
    val desc: String,
    val question: List<ShuffledQuestionData>
)