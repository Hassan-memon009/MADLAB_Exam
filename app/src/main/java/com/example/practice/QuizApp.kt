package com.example.practice

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            QuizApp(navController = navController)
        }
    }
}

@Composable
fun QuizApp(navController: NavController) {
    val questions = fetchQuestions()

    var currentQuestionIndex by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = questions[currentQuestionIndex].questionText,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            questions[currentQuestionIndex].options.forEachIndexed { index, option ->
                OptionButton(
                    optionText = option,
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OptionButton(optionText: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Text(text = optionText)
    }
}

@Composable
fun fetchQuestions(): List<Question> {
    val context = LocalContext.current
    val resources = context.resources

    val questions = mutableListOf<Question>()

    val questionTexts = listOf(
        R.string.question_1,
        R.string.question_2,
        R.string.question_3,
        R.string.question_4,
        R.string.question_5
    )

    val optionArrays = listOf(
        R.array.options_question_1,
        R.array.options_question_2,
        R.array.options_question_3,
        R.array.options_question_4,
        R.array.options_question_5
    )

    val answerIndices = listOf(
        R.integer.answer_index_1,
        R.integer.answer_index_2,
        R.integer.answer_index_3,
        R.integer.answer_index_4,
        R.integer.answer_index_5,

    )

    for (i in questionTexts.indices) {
        val questionText = resources.getString(questionTexts[i])
        val optionsArray = resources.getStringArray(optionArrays[i])
        val correctAnswerIndex = resources.getInteger(answerIndices[i])

        val question = Question(questionText, optionsArray.toList(), correctAnswerIndex)
        questions.add(question)
    }

    return questions
}

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

@Preview(showBackground = true)
@Composable
fun QuizAppPreview() {
    App()
}
