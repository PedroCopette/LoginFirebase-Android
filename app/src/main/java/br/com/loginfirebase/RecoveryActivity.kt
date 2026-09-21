package br.com.loginfirebase

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme

class RecoveryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginFirebaseTheme {

                var email by remember {
                    mutableStateOf("")
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("RECUPERAÇÃO DE SENHA")

                    Spacer(modifier = Modifier.height(30.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text("E-mail")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {

                            Toast.makeText(
                                this@RecoveryActivity,
                                "E-mail de recuperação enviado!",
                                Toast.LENGTH_SHORT
                            ).show()

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ENVIAR")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            finish()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("VOLTAR")
                    }
                }
            }
        }
    }
}