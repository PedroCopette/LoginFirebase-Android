package br.com.loginfirebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class EventoBusca(
    val id: String,
    val nome: String,
    val data: String,
    val horario: String,
    val local: String,
    val descricao: String,
    val categoria: String
)

class EventsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val eventos = listOf(
            EventoBusca(
                id = "semana_academica",
                nome = "Semana Acadêmica",
                data = "20/09/2026",
                horario = "19:00",
                local = "Auditório da URI",
                descricao = "Evento acadêmico com palestras, apresentações e atividades para os estudantes.",
                categoria = "Acadêmico"
            ),
            EventoBusca(
                id = "hackathon_campushub",
                nome = "Hackathon CampusHub",
                data = "25/09/2026",
                horario = "08:00",
                local = "Laboratório de Informática",
                descricao = "Competição de programação e desenvolvimento de soluções tecnológicas.",
                categoria = "Tecnologia"
            ),
            EventoBusca(
                id = "palestra_tecnologia",
                nome = "Palestra de Tecnologia",
                data = "30/09/2026",
                horario = "20:00",
                local = "Sala 12",
                descricao = "Palestra sobre tecnologia, inovação e tendências da área de computação.",
                categoria = "Tecnologia"
            )
        )

        setContent {
            LoginFirebaseTheme {

                var busca by remember {
                    mutableStateOf("")
                }

                var categoriaSelecionada by remember {
                    mutableStateOf("Todas")
                }

                var situacaoSelecionada by remember {
                    mutableStateOf("Todos")
                }

                var menuCategoriaAberto by remember {
                    mutableStateOf(false)
                }

                var menuSituacaoAberto by remember {
                    mutableStateOf(false)
                }

                val eventosFiltrados = eventos.filter { evento ->

                    val correspondeBusca =
                        evento.nome.contains(
                            busca,
                            ignoreCase = true
                        )

                    val correspondeCategoria =
                        categoriaSelecionada == "Todas" ||
                                evento.categoria == categoriaSelecionada

                    val correspondeSituacao =
                        when (situacaoSelecionada) {

                            "Próximos" ->
                                !eventoEncerrado(
                                    evento.data,
                                    evento.horario
                                )

                            "Encerrados" ->
                                eventoEncerrado(
                                    evento.data,
                                    evento.horario
                                )

                            else -> true
                        }

                    correspondeBusca &&
                            correspondeCategoria &&
                            correspondeSituacao
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("EVENTOS DISPONÍVEIS")

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    OutlinedTextField(
                        value = busca,
                        onValueChange = {
                            busca = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Buscar evento")
                        },
                        placeholder = {
                            Text("Digite o título do evento")
                        },
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    Button(
                        onClick = {
                            menuCategoriaAberto = true
                        }
                    ) {
                        Text("Categoria: $categoriaSelecionada")
                    }

                    DropdownMenu(
                        expanded = menuCategoriaAberto,
                        onDismissRequest = {
                            menuCategoriaAberto = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("Todas")
                            },
                            onClick = {
                                categoriaSelecionada = "Todas"
                                menuCategoriaAberto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Acadêmico")
                            },
                            onClick = {
                                categoriaSelecionada = "Acadêmico"
                                menuCategoriaAberto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Tecnologia")
                            },
                            onClick = {
                                categoriaSelecionada = "Tecnologia"
                                menuCategoriaAberto = false
                            }
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {
                            menuSituacaoAberto = true
                        }
                    ) {
                        Text("Situação: $situacaoSelecionada")
                    }

                    DropdownMenu(
                        expanded = menuSituacaoAberto,
                        onDismissRequest = {
                            menuSituacaoAberto = false
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text("Todos")
                            },
                            onClick = {
                                situacaoSelecionada = "Todos"
                                menuSituacaoAberto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Próximos")
                            },
                            onClick = {
                                situacaoSelecionada = "Próximos"
                                menuSituacaoAberto = false
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text("Encerrados")
                            },
                            onClick = {
                                situacaoSelecionada = "Encerrados"
                                menuSituacaoAberto = false
                            }
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    if (eventosFiltrados.isEmpty()) {

                        Text("Nenhum evento encontrado.")

                    } else {

                        eventosFiltrados.forEach { evento ->

                            Button(
                                onClick = {
                                    abrirDetalhes(
                                        id = evento.id,
                                        nome = evento.nome,
                                        data = evento.data,
                                        horario = evento.horario,
                                        local = evento.local,
                                        descricao = evento.descricao
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(evento.nome)
                            }

                            Spacer(
                                modifier = Modifier.height(15.dp)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
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

    private fun eventoEncerrado(
        data: String,
        horario: String
    ): Boolean {

        val formato = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        )

        val dataEvento = formato.parse(
            "$data $horario"
        ) ?: return false

        return Date().after(dataEvento)
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