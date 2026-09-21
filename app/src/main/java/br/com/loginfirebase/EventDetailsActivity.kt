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
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme

class EventDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nomeEvento = intent.getStringExtra("nomeEvento") ?: "Evento"
        val dataEvento = intent.getStringExtra("dataEvento") ?: "Data não informada"
        val horarioEvento = intent.getStringExtra("horarioEvento") ?: "Horário não informado"
        val localEvento = intent.getStringExtra("localEvento") ?: "Local não informado"
        val descricaoEvento = intent.getStringExtra("descricaoEvento")
            ?: "Descrição não informada"

        setContent {
            LoginFirebaseTheme {

                var inscrito by remember {
                    mutableStateOf(false)
                }

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
                            inscrito = !inscrito
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
}