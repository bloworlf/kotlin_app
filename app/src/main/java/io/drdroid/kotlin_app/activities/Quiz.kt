package io.drdroid.kotlin_app.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import io.drdroid.kotlin_app.R
import io.drdroid.kotlin_app.base.BaseActivity
import io.drdroid.kotlin_app.databinding.ActivityQuizBinding
import io.drdroid.kotlin_app.models.Answer
import io.drdroid.kotlin_app.models.Question


class Quiz : BaseActivity() {

    lateinit var binding: ActivityQuizBinding

    lateinit var quizzes: MutableList<Question>

    var score: Int = 0
    var questionIndex: Int = 0
    var chosenAnswers: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = resources.getColor(R.color.light_blue, null)
//        window.navigationBarColor = resources.getDrawable(R.drawable.activity_bg, null)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //creating quiz
        quizzes = mutableListOf<Question>()
        quizzes.addAll(
            listOf(
                Question(
                    id = 1,
                    text = "What is the value of π (PI)?", answers = listOf(
                        Answer(id = 1, text = "31.4159", correct = false),
                        Answer(id = 2, text = Math.PI.toString().slice(0..6), correct = true),
                        Answer(id = 3, text = "314.159", correct = false),
                        Answer(id = 4, text = "0.31415", correct = false),
                    )
                ),
                Question(
                    id = 2,
                    text = "What is the color of the sky at night?", answers = listOf(
                        Answer(id = 5, text = "Blue", correct = false),
                        Answer(id = 6, text = "Black", correct = true),
                    )
                ),
                Question(
                    id = 3,
                    text = "Which one of those is mammal?",
                    answers = listOf(
                        Answer(id = 7, text = "Cat", correct = true),
                        Answer(id = 8, text = "Eagle", correct = false),
                        Answer(id = 9, text = "Platypus", correct = true),
                        Answer(id = 10, text = "Elephant", correct = true),
                    )
                ),
                Question(
                    id = 4,
                    text = "How fast is sound in the air at sea level?",
                    answers = listOf(
                        Answer(id = 11, text = "300 m/s", correct = false),
                        Answer(id = 12, text = "1100 ft/s", correct = true),
                        Answer(id = 13, text = "1562.4 km/h", correct = true),
                        Answer(id = 14, text = "700 mph", correct = false),
                    )
                ),
                Question(
                    id = 5,
                    text = "What is the boiling temperature of water?",
                    answers = listOf(
                        Answer(id = 15, text = "212°F", correct = false),
                        Answer(id = 16, text = "100°C", correct = false),
                        Answer(id = 17, text = "The two numbers", correct = false),
                        Answer(id = 18, text = "All of the answers", correct = true),
                    )
                ),
                Question(
                    id = 6,
                    text = "A child's blood type is AB. What are the possible blood type of his parents?",
                    answers = listOf(
                        Answer(id = 19, text = "B and AB", correct = true),
                        Answer(id = 20, text = "O and B", correct = false),
                        Answer(id = 21, text = "AB and O", correct = false),
                        Answer(id = 22, text = "A and B", correct = true),
                    )
                ),
            )
        )

        val questionLayout: LinearLayout = binding.questionLayout
        val txtScore: TextView = binding.score

        displayQuestion(questionIndex, questionLayout)

        val answerBtn: AppCompatButton = binding.answerQuestion
        answerBtn.setOnClickListener {

            //check chosen answer first
            val rightAnswers = quizzes[questionIndex].rightAnswers()
//            var selection = quizzes[questionIndex].selectedAnswers()
//            if (selection.isEmpty()) {
//                Toast.makeText(this@Quiz, "Please, select an answer.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            if (chosenAnswers.isEmpty()) {
                Toast.makeText(this@Quiz, "Please, select an answer.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            checkAnswers(rightAnswers, chosenAnswers, txtScore)
            //move to next question
            questionIndex++
            if (questionIndex == quizzes.size) {
                questionIndex = 0
                score = 0
            }
            chosenAnswers = mutableListOf()
            displayQuestion(questionIndex, questionLayout)
        }
    }

    private fun checkAnswers(answers: List<Int>, selected: List<Int>, txtView: TextView) {
        selected.forEach {
            if (it in answers) {
                //increase score
                score += 2
            } else {
                //decrease score?
                if (score > 0) {
                    score--
                }
            }

            txtView.text = "Score: $score"
        }
    }

    private fun displayQuestion(index: Int, layout: LinearLayout) {
        if (layout.childCount > 1) {
            //remove the displayed question
            layout.removeViews(0, 1)
        }
        //create the question view and add to layout
        layout.addView(createQuestionChild(quizzes[index]), 0)
    }

    fun createQuestionChild(question: Question): View {
        val view: View = layoutInflater.inflate(R.layout.model_question, null)

        val q: TextView = view.findViewById(R.id.question)
        q.text = question.text

        val aLayout: LinearLayout = view.findViewById(R.id.answer_layout)
        if (aLayout.childCount > 0) {
            aLayout.removeAllViews()
        }

//        question.answers = question.answers.shuffled()
        if (question.hasMultipleAnswer()) {
            question.answers.forEach {
                aLayout.addView(createMultipleAnswerChild(it))
            }
        } else {
            aLayout.addView(createSingleAnswerChild(question.answers))
        }

        return view
    }

    private fun createMultipleAnswerChild(answer: Answer): View {
        val view: View = layoutInflater.inflate(R.layout.model_answer_chk, null)

        val a: TextView = view.findViewById(R.id.answer_text)
        a.text = answer.text

        val chk: CheckBox = view.findViewById(R.id.answer_chk)
        chk.setOnCheckedChangeListener { compoundButton, b ->
//            if (compoundButton.isShown) {
//                answer.onMultiChoice(answer.id, b)
//            }
            if (b) {
                chosenAnswers.add(answer.id)
            } else {
                chosenAnswers.remove(answer.id)
            }
        }

        return view
    }

    private fun createSingleAnswerChild(answers: List<Answer>): View {
//        var view: View = layoutInflater.inflate(R.layout.model_answer_radio, null)

        var group = RadioGroup(this@Quiz)
        val gParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        group.layoutParams = gParams
        group.orientation = LinearLayout.VERTICAL
        group.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        group.dividerDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.vertical_divider, null)

//        if (group.childCount > 0) {
//            group.removeAllViews()
//        }

        var id: Int = 1
        answers.forEach {
            //create a radio button
            var radio = RadioButton(group.context)
            val params =
                LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER
            radio.gravity = Gravity.END
//            val font = ResourcesCompat.getFont(this@Quiz, R.font.aller_light)
            radio.typeface = ResourcesCompat.getFont(this@Quiz, R.font.aller_light)
            radio.textSize = 18F
//            radio.layoutDirection = View.LAYOUT_DIRECTION_RTL
            radio.layoutParams = params
            radio.text = it.text
            radio.id = id

            group.addView(radio)
            id++
        }

        group.setOnCheckedChangeListener { radioGroup, i ->
//            answers[i - 1].onSingleChoice(answers[i - 1].id)
            if (chosenAnswers.size > 0) {
                chosenAnswers.clear()
            }
            chosenAnswers.add(answers[i - 1].id)
        }

//        val a: TextView = view.findViewById(R.id.answer_text)
//        a.text = answer.text
//
//        val chk: CheckBox = view.findViewById(R.id.answer_chk)
//        chk.setOnCheckedChangeListener { compoundButton, b ->
//            answer.onAnswerSelected(answer.id, b)
//        }

        return group
    }
}