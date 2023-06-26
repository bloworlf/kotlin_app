package io.drdroid.kotlin_app.models

//interface SelectionListener {
//    fun onAnswerSelected(id: Int, checked: Boolean)
//}

class Answer(val id: Int, val text: String, val correct: Boolean) : Question.SelectionListener