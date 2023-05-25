package com.kamunanya

import com.google.gson.Gson

data class QuizData(var id: Long=0, var title: String, var desc: String, var question: List<QuestionData>) {

    private lateinit var shuffledQuestion: List<QuestionData>

    fun shuffleAnswer() {
        val avail = question.indices.toMutableList()
        val questionList = MutableList<QuestionData>(question.size) {
            QuestionData("", "",  listOf<String>())
        }
        for(q in question) {
            val selected = question.indices.random()
            val selectedValue = avail.removeAt(selected)
            questionList[selectedValue] = question[selectedValue]
        }
        this.shuffledQuestion = questionList
    }

    fun getShuffledQustion(): List<QuestionData> {
        return shuffledQuestion
    }

    fun asJson(): String {
        return Gson().toJson(this)
    }

    fun toUri() {}

    companion object {
        fun fromJson(json: String): QuizData {
            return Gson().fromJson(json, QuizData::class.java)
        }
    }
}