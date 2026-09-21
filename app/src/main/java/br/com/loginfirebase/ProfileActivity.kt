package br.com.loginfirebase

import android.content.Context
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
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme

class ProfileActivity : ComponentActivity() {

    private var nomeUsuario by mutableStateOf("")
    private var emailUsuario by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        carregarPerfil()

        setContent {
            LoginFirebaseTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("MEU PERFIL")

                    Spacer(modifier = Modifier.height(30.dp))

                    Text("Nome: $nomeUsuario")

                    Spacer(modifier = Modifier.height(15.dp))

                    Text("E-mail: $emailUsuario")

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {
                            startActivity(
                                android.content.Intent(
                                    this@ProfileActivity,
                                    EditProfileActivity::class.java
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("EDITAR PERFIL")
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

    override fun onResume() {
        super.onResume()
        carregarPerfil()
    }

    private fun carregarPerfil() {

        val preferencias = getSharedPreferences(
            "CampusHubPrefs",
            Context.MODE_PRIVATE
        )

        nomeUsuario = preferencias.getString(
            "nome",
            "Pedro Copette"
        ) ?: "Pedro Copette"

        emailUsuario = preferencias.getString(
            "email",
            "pedro@email.com"
        ) ?: "pedro@email.com"
    }
}