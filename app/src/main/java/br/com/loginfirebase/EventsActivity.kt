package br.com.loginfirebase

import android.content.Intent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme

class EventsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginFirebaseTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(25.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("EVENTOS DISPONÍVEIS")

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            abrirDetalhes(
                                id = "semana_academica",
                                nome = "Semana Acadêmica",
                                data = "20/09/2026",
                                horario = "19:00",
                                local = "Auditório da URI",
                                descricao = "Evento acadêmico com palestras, apresentações e atividades para os estudantes."
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Semana Acadêmica")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            abrirDetalhes(
                                id = "hackathon_campushub",
                                nome = "Hackathon CampusHub",
                                data = "25/09/2026",
                                horario = "08:00",
                                local = "Laboratório de Informática",
                                descricao = "Competição de programação e desenvolvimento de soluções tecnológicas."
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Hackathon CampusHub")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            abrirDetalhes(
                                id = "palestra_tecnologia",
                                nome = "Palestra de Tecnologia",
                                data = "30/09/2026",
                                horario = "20:00",
                                local = "Sala 12",
                                descricao = "Palestra sobre tecnologia, inovação e tendências da área de computação."
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Palestra de Tecnologia")
                    }

                    Spacer(modifier = Modifier.height(25.dp))

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

    private fun abrirDetalhes(
        id: String,
        nome: String,
        data: String,
        horario: String,
        local: String,
        descricao: String
    ) {

        val intent = Intent(
            this,
            EventDetailsActivity::class.java
        )

        intent.putExtra("idEvento", id)
        intent.putExtra("nomeEvento", nome)
        intent.putExtra("dataEvento", data)
        intent.putExtra("horarioEvento", horario)
        intent.putExtra("localEvento", local)
        intent.putExtra("descricaoEvento", descricao)

        startActivity(intent)
    }
}