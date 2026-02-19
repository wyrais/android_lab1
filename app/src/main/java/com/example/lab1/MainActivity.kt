package com.example.lab1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1.ui.theme.Lab1Theme

val Roboto = FontFamily(
    Font(R.font.roboto_regular)
)
val SourceCodePro = FontFamily(
    Font(R.font.sourcecodepro_regular)
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FontApp()
                }
            }
        }
    }
}

@Composable
fun FontApp() {
    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }
    var selectedFontKey by remember { mutableStateOf<String?>(null) }
    var resultText by remember { mutableStateOf("") }

    var resultFont by remember { mutableStateOf<FontFamily?>(null) }

    val fonts = listOf(
        "Sans Serif" to Roboto,
        "Serif" to FontFamily.Serif,
        "Monospace" to SourceCodePro,
        "Cursive" to FontFamily.Cursive,
        "Default" to FontFamily.Default
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Оберіть шрифт:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        fonts.forEach { (name, family) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedFontKey = name }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (selectedFontKey == name),
                    onClick = { selectedFontKey = name }
                )
                Text(
                    text = name,
                    fontFamily = family,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Введіть текст") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (inputText.isBlank() || selectedFontKey == null) {
                        Toast.makeText(
                            context,
                            "Введіть текст та оберіть шрифт!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        resultText = inputText
                        resultFont = fonts.find { it.first == selectedFontKey }?.second
                    }
                }
            ) {
                Text("OK")
            }

            Spacer(modifier = Modifier.width(16.dp))

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    inputText = ""
                    resultText = ""
                    selectedFontKey = null
                    resultFont = null
                }
            ) {
                Text("Cancel")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (resultText.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = resultText,
                        fontSize = 24.sp,
                        fontFamily = resultFont
                    )
                }
            }
        }
    }
}
