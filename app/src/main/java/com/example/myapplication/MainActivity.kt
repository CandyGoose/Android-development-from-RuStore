package com.example.myapplication

import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.navigation.AppNavGraph
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { _ ->
                    AppNavGraph(
                        modifier = Modifier
                            .fillMaxSize(),
                        onShowMessage = { message ->
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun IntentScreen(
    modifier: Modifier = Modifier,
    onShowMessage: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.hint_edit_text)) },
            singleLine = false,
            modifier = Modifier.fillMaxSize(0.4f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, SecondActivity::class.java).apply {
                    putExtra(EXTRA_TEXT, text)
                }
                context.startActivity(intent)
            }
        ) {
            Text(stringResource(R.string.btn_open_second))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val input = text.trim()
                if (input.isEmpty()) {
                    onShowMessage(context.getString(R.string.error_empty_text))
                    return@Button
                }
                val hasLetters = input.any { c -> c.isLetter() }
                if (hasLetters) {
                    onShowMessage(context.getString(R.string.error_phone_no_letters))
                    return@Button
                }
                val digitsOnly = input.filter { c -> c.isDigit() || c == '+' }
                if (digitsOnly.length < 10 || !digitsOnly.any { c -> c.isDigit() }) {
                    onShowMessage(context.getString(R.string.error_invalid_phone))
                    return@Button
                }
                val uri = Uri.parse("tel:${Uri.encode(digitsOnly)}")
                val intent = Intent(Intent.ACTION_DIAL).apply { data = uri }
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    onShowMessage(context.getString(R.string.error_invalid_phone))
                }
            }
        ) {
            Text(stringResource(R.string.btn_call_friend))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val input = text.trim()
                if (input.isEmpty()) {
                    onShowMessage(context.getString(R.string.error_empty_share))
                    return@Button
                }
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, input)
                    type = "text/plain"
                }
                try {
                    context.startActivity(Intent.createChooser(sendIntent, null))
                } catch (e: ActivityNotFoundException) {
                    onShowMessage(context.getString(R.string.error_empty_share))
                }
            }
        ) {
            Text(stringResource(R.string.btn_share_text))
        }
    }
}
