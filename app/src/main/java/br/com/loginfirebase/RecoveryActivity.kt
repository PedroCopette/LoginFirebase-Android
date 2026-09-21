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
import com.google.firebase.auth.FirebaseAuth

class RecoveryActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

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

                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )

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

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    Button(
                        onClick = {
                            enviarRecuperacao(email)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ENVIAR")
                    }

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

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

    private fun enviarRecuperacao(email: String) {

        val emailFinal = email.trim()

        if (emailFinal.isEmpty()) {
            Toast.makeText(
                this,
                "Digite seu e-mail.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        auth.sendPasswordResetEmail(emailFinal)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        "E-mail de recuperação enviado!",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()

                } else {

                    Toast.makeText(
                        this,
                        "Não foi possível enviar o e-mail de recuperação.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}