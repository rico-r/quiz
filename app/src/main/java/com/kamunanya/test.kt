package com.kamunanya

import com.google.gson.Gson

fun main() {
    val gson = Gson()
    var quiz = QuizData(
        0,
        "ini judul",
        "ini deskripsi",
        listOf<QuestionData>(
            QuestionData("pertanyaan 1", "jwb", listOf<String>("alt1", "alt2", "alt3"))
        )
    )

    val q1 = quiz.question[0]
    q1.shuffleAnswer()
    println("Answer index: ${q1.getCorrectAnswerIndex()}")
    val ansList = q1.getShuffledAnswer().forEach{
        println(it)
    }
    println()
    val json = quiz.asJson();
    println(json)
//    val quizFromJson = QuizData.fromJson(json)
//    println(quizFromJson.toString())
}