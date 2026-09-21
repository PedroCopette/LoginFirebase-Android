package br.com.loginfirebase

import android.content.Intent
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            LoginFirebaseTheme {

                TelaLogin(
                    entrar = { email, senha ->
                        fazerLogin(email, senha)
                    },

                    abrirCadastro = {
                        startActivity(
                            Intent(
                                this,
                                RegistrationActivity::class.java
                            )
                        )
                    },

                    recuperarSenha = {
                        startActivity(
                            Intent(
                                this,
                                RecoveryActivity::class.java
                            )
                        )
                    }
                )
            }
        }
    }

    private fun fazerLogin(
        email: String,
        senha: String
    ) {

        val emailFinal = email.trim()
        val senhaFinal = senha.trim()

        if (emailFinal.isEmpty() || senhaFinal.isEmpty()) {

            Toast.makeText(
                this,
                "Preencha e-mail e senha.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        auth.signInWithEmailAndPassword(
            emailFinal,
            senhaFinal
        ).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {

                Toast.makeText(
                    this,
                    "Login realizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        DashboardActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "E-mail ou senha incorretos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun TelaLogin(
    entrar: (String, String) -> Unit,
    abrirCadastro: () -> Unit,
    recuperarSenha: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text("LOGIN")

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
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = senha,
            onValueChange = {
                senha = it
            },
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Button(
            onClick = {
                entrar(email, senha)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ENTRAR")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
                abrirCadastro()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CADASTRAR")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
                recuperarSenha()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ESQUECI MINHA SENHA")
        }
    }
}