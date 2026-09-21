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

class EventDetailsActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var inscrito by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idEvento = intent.getStringExtra("idEvento") ?: ""
        val nomeEvento = intent.getStringExtra("nomeEvento") ?: "Evento"
        val dataEvento = intent.getStringExtra("dataEvento") ?: "Data não informada"
        val horarioEvento = intent.getStringExtra("horarioEvento") ?: "Horário não informado"
        val localEvento = intent.getStringExtra("localEvento") ?: "Local não informado"
        val descricaoEvento = intent.getStringExtra("descricaoEvento")
            ?: "Descrição não informada"

        verificarInscricao(idEvento)

        setContent {
            LoginFirebaseTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("DETALHES DO EVENTO")

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(nomeEvento)

                    Spacer(modifier = Modifier.height(15.dp))

                    Text("Data: $dataEvento")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Horário: $horarioEvento")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Local: $localEvento")

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(descricaoEvento)

                    Spacer(modifier = Modifier.height(25.dp))

                    Button(
                        onClick = {
                            if (inscrito) {
                                cancelarInscricao(idEvento)
                            } else {
                                fazerInscricao(
                                    idEvento,
                                    nomeEvento,
                                    dataEvento,
                                    horarioEvento,
                                    localEvento
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        if (inscrito) {
                            Text("CANCELAR INSCRIÇÃO")
                        } else {
                            Text("INSCREVER-SE")
                        }
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

    private fun verificarInscricao(idEvento: String) {

        val usuario = auth.currentUser ?: return

        val idInscricao = "${usuario.uid}_$idEvento"

        db.collection("inscricoes")
            .document(idInscricao)
            .get()
            .addOnSuccessListener { documento ->

                inscrito = documento.exists()
            }
    }

    private fun fazerInscricao(
        idEvento: String,
        nomeEvento: String,
        dataEvento: String,
        horarioEvento: String,
        localEvento: String
    ) {

        val usuario = auth.currentUser

        if (usuario == null) {
            Toast.makeText(
                this,
                "Faça login para se inscrever.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val idInscricao = "${usuario.uid}_$idEvento"

        val dados = hashMapOf(
            "idEvento" to idEvento,
            "nomeEvento" to nomeEvento,
            "dataEvento" to dataEvento,
            "horarioEvento" to horarioEvento,
            "localEvento" to localEvento,
            "usuarioId" to usuario.uid,
            "usuarioEmail" to usuario.email
        )

        db.collection("inscricoes")
            .document(idInscricao)
            .set(dados)
            .addOnSuccessListener {

                inscrito = true

                Toast.makeText(
                    this,
                    "Inscrição realizada!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao realizar inscrição.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun cancelarInscricao(idEvento: String) {

        val usuario = auth.currentUser ?: return

        val idInscricao = "${usuario.uid}_$idEvento"

        db.collection("inscricoes")
            .document(idInscricao)
            .delete()
            .addOnSuccessListener {

                inscrito = false

                Toast.makeText(
                    this,
                    "Inscrição cancelada!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao cancelar inscrição.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}