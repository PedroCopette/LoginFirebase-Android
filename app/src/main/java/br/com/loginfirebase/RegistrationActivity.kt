package br.com.loginfirebase

import android.content.Context
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
import androidx.compose.runtime.Composable
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
import com.google.firebase.auth.UserProfileChangeRequest

class RegistrationActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            LoginFirebaseTheme {
                TelaCadastro(
                    registrar = { nome, email, senha ->
                        cadastrarUsuario(nome, email, senha)
                    },
                    voltar = {
                        finish()
                    }
                )
            }
        }
    }

    private fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String
    ) {

        val nomeFinal = nome.trim()
        val emailFinal = email.trim()
        val senhaFinal = senha.trim()

        if (nomeFinal.isEmpty() ||
            emailFinal.isEmpty() ||
            senhaFinal.isEmpty()
        ) {
            Toast.makeText(
                this,
                "Preencha todos os campos.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (senhaFinal.length < 6) {
            Toast.makeText(
                this,
                "A senha deve ter pelo menos 6 caracteres.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        auth.createUserWithEmailAndPassword(
            emailFinal,
            senhaFinal
        ).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {

                val usuario = auth.currentUser

                if (usuario != null) {

                    val atualizacaoPerfil =
                        UserProfileChangeRequest.Builder()
                            .setDisplayName(nomeFinal)
                            .build()

                    usuario.updateProfile(atualizacaoPerfil)
                        .addOnCompleteListener {

                            val preferencias = getSharedPreferences(
                                "CampusHubPrefs",
                                Context.MODE_PRIVATE
                            )

                            preferencias.edit()
                                .putString("nome", nomeFinal)
                                .putString("email", emailFinal)
                                .apply()

                            auth.signOut()

                            Toast.makeText(
                                this,
                                "Conta criada com sucesso!",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        }

                } else {

                    Toast.makeText(
                        this,
                        "Erro ao criar o perfil.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {

                Toast.makeText(
                    this,
                    "Erro ao criar conta: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Composable
fun TelaCadastro(
    registrar: (String, String, String) -> Unit,
    voltar: () -> Unit
) {

    var nome by remember {
        mutableStateOf("")
    }

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

        Text("CRIAR CONTA")

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        OutlinedTextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            label = {
                Text("Nome")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(15.dp)
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
                registrar(nome, email, senha)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("REGISTRAR")
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

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