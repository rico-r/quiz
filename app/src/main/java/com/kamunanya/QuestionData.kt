package com.kamunanya

data class QuestionData(
        val question:String,
        val ans: String,
        val alt: List<String>
) {

    fun shuffle(): ShuffledQuestionData {
        val avail = (0..alt.size).toMutableList()
        val answers = MutableList(alt.size + 1) { "" }
        val correctAnswerIndex = (0 until avail.size).random()
        avail.removeAt(correctAnswerIndex)
        answers[correctAnswerIndex] = ans
        for(alternateAnswer in alt) {
            val selected = (0 until avail.size).random()
            val selectedValue = avail.removeAt(selected)
            answers[selectedValue] = alternateAnswer
        }
        return ShuffledQuestionData(question, correctAnswerIndex, answers)
    }

}

data class ShuffledQuestionData(
        val question:String,
        val correctAnswerIndex: Int,
        val answer: List<String>
)
