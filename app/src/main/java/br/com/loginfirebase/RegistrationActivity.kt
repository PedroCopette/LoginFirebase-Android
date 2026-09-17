package br.com.loginfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme

class RegistrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginFirebaseTheme {
                TelaCadastro(
                    voltar = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun TelaCadastro(voltar: () -> Unit) {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "CRIAR CONTA"
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = {
                Text("Nome")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("E-mail")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("REGISTRAR")
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                voltar()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VOLTAR")
        }
    }
}