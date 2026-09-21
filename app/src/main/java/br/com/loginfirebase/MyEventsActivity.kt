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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyEventsActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var eventosInscritos by mutableStateOf<List<Evento>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginFirebaseTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(25.dp)
                        .verticalScroll(rememberScrollState()),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("MEUS EVENTOS")

                    Spacer(modifier = Modifier.height(30.dp))

                    if (eventosInscritos.isEmpty()) {

                        Text("Você ainda não está inscrito em nenhum evento.")

                    } else {

                        eventosInscritos.forEach { evento ->

                            Text(evento.nome)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Data: ${evento.data}")

                            Spacer(modifier = Modifier.height(5.dp))

                            Text("Horário: ${evento.horario}")

                            Spacer(modifier = Modifier.height(5.dp))

                            Text("Local: ${evento.local}")

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

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
        carregarEventos()
    }

    private fun carregarEventos() {

        val usuario = auth.currentUser

        if (usuario == null) {

            Toast.makeText(
                this,
                "Usuário não está logado.",
                Toast.LENGTH_SHORT
            ).show()

            finish()

            return
        }

        db.collection("inscricoes")
            .whereEqualTo("usuarioId", usuario.uid)
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull { documento ->

                    val nome = documento.getString("nomeEvento")
                    val data = documento.getString("dataEvento")
                    val horario = documento.getString("horarioEvento")
                    val local = documento.getString("localEvento")

                    if (
                        nome != null &&
                        data != null &&
                        horario != null &&
                        local != null
                    ) {

                        Evento(
                            nome = nome,
                            data = data,
                            horario = horario,
                            local = local
                        )

                    } else {
                        null
                    }
                }

                eventosInscritos = lista
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao carregar seus eventos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

data class Evento(
    val nome: String,
    val data: String,
    val horario: String,
    val local: String
)