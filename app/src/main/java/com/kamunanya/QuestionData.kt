package com.kamunanya

data class QuestionData(val question:String, val ans: String, var alt: List<String>) {
    private lateinit var answerList: List<String>
    private var correctAnswerIndex: Int = -1

    fun shuffleAnswer() {
        val avail = (0..alt.size).toMutableList()
        val answerList = MutableList(alt.size + 1) { "" }
        correctAnswerIndex = (0 until avail.size).random()
        avail.removeAt(correctAnswerIndex)
        answerList[correctAnswerIndex] = ans
        for(alternateAnswer in alt) {
            val selected = (0 until avail.size).random()
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
