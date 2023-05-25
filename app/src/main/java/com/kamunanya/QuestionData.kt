package com.kamunanya

data class QuestionData(val question:String, val ans: String, var alt: List<String>) {
    private lateinit var answerList: List<String>
    private var correctAnswerIndex: Int = -1

    fun shuffleAnswer() {
        val avail = (0..alt.size).toMutableList()
        val answerList = MutableList<String>(alt.size + 1) { "" }
        correctAnswerIndex = (0..(avail.size - 1)).random()
        avail.removeAt(correctAnswerIndex)
        answerList[correctAnswerIndex] = ans
        for(alternateAnswer in alt) {
            val selected = (0..avail.size - 1).random()
            val selectedValue = avail.removeAt(selected)
            answerList[selectedValue] = alternateAnswer
        }
        this.answerList = answerList
    }

    fun getShuffledAnswer(): List<String> {
        return answerList
    }

    fun getCorrectAnswerIndex(): Int {
        return correctAnswerIndex
    }
}
