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
import com.google.firebase.auth.UserProfileChangeRequest

class EditProfileActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usuario = auth.currentUser

        if (usuario == null) {
            finish()
            return
        }

        val nomeAtual = usuario.displayName ?: ""

        val emailAtual = usuario.email ?: ""

        setContent {
            LoginFirebaseTheme {

                var nome by remember {
                    mutableStateOf(nomeAtual)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("EDITAR PERFIL")

                    Spacer(modifier = Modifier.height(30.dp))

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

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = emailAtual,
                        onValueChange = {},
                        enabled = false,
                        label = {
                            Text("E-mail")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {

                            val nomeFinal = nome.trim()

                            if (nomeFinal.isEmpty()) {
                                Toast.makeText(
                                    this@EditProfileActivity,
                                    "Digite um nome.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }

                            val atualizacao =
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(nomeFinal)
                                    .build()

                            usuario.updateProfile(atualizacao)
                                .addOnCompleteListener { task ->

                                    if (task.isSuccessful) {

                                        Toast.makeText(
                                            this@EditProfileActivity,
                                            "Perfil atualizado!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        finish()

                                    } else {

                                        Toast.makeText(
                                            this@EditProfileActivity,
                                            "Não foi possível atualizar o perfil.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SALVAR")
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