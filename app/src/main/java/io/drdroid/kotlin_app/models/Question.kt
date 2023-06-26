package io.drdroid.kotlin_app.models

var chosen: MutableList<Int> = mutableListOf()

open class Question(val id: Int, val text: String, var answers: List<Answer>) {
//    private var chosen: MutableList<Int> = mutableListOf()

    init {
        answers = answers.shuffled()
    }

    fun hasMultipleAnswer(): Boolean {
        return answers.filter { it.correct }.size > 1
    }

    companion object
    interface SelectionListener {
        fun onMultiChoice(id: Int, checked: Boolean) {
            if (checked) {
                chosen.add(id)
            } else {
                chosen.remove(id)
            }
        }

        fun onSingleChoice(id: Int) {
            if (chosen.isNotEmpty()) {
                chosen.clear()
            }
            chosen.add(id)
        }
    }

    fun rightAnswers(): List<Int> {
        return answers.filter { it.correct }.map { it.id }
    }

    fun selectedAnswers(): List<Int> {
        return chosen
    }
}