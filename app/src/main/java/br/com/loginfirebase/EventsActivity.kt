package br.com.loginfirebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val EventsNavy = Color(0xFF172554)
private val EventsBlue = Color(0xFF3568D4)
private val EventsBackground = Color(0xFFF3F6FC)
private val EventsText = Color(0xFF24324A)
private val EventsMuted = Color(0xFF718096)

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

                // A tela inteira pode rolar para exibir todo o conteúdo.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(EventsBackground)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .verticalScroll(rememberScrollState())
                ) {

                    // Cabeçalho
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(
                                    bottomStart = 30.dp,
                                    bottomEnd = 30.dp
                                )
                            )
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        EventsNavy,
                                        EventsBlue
                                    )
                                )
                            )
                            .padding(
                                horizontal = 24.dp,
                                vertical = 25.dp
                            )
                    ) {

                        Column {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(
                                            RoundedCornerShape(15.dp)
                                        )
                                        .background(
                                            Color.White.copy(alpha = 0.17f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "CH",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.width(12.dp)
                                )

                                Column {

                                    Text(
                                        text = "CampusHub",
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "EVENTOS UNIVERSITÁRIOS",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 10.sp,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(23.dp)
                            )

                            Text(
                                text = "Explore os eventos",
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Descubra oportunidades e atividades no campus.",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Área dos filtros e dos eventos
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {

                        Spacer(
                            modifier = Modifier.height(22.dp)
                        )

                        Text(
                            text = "Encontre seu próximo evento",
                            color = EventsText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "Busque pelo nome ou aplique os filtros.",
                            color = EventsMuted,
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
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
                            leadingIcon = {
                                Text(
                                    text = "⌕",
                                    fontSize = 25.sp,
                                    color = EventsBlue
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = EventsBlue,
                                unfocusedBorderColor = Color(0xFFD6DEEA),
                                focusedLabelColor = EventsBlue,
                                cursorColor = EventsBlue,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Filtros",
                            color = EventsText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            // Filtro de categoria
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {

                                Button(
                                    onClick = {
                                        menuCategoriaAberto = true
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(13.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = EventsNavy
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 1.dp
                                    )
                                ) {
                                    Text(
                                        text = "Categoria: $categoriaSelecionada",
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
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
                            }

                            // Filtro de situação
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {

                                Button(
                                    onClick = {
                                        menuSituacaoAberto = true
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(13.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = EventsNavy
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 1.dp
                                    )
                                ) {
                                    Text(
                                        text = "Situação: $situacaoSelecionada",
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
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
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(22.dp)
                        )

                        Text(
                            text = "${eventosFiltrados.size} evento(s) encontrado(s)",
                            color = EventsMuted,
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        if (eventosFiltrados.isEmpty()) {

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(25.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = "🔎",
                                        fontSize = 32.sp
                                    )

                                    Spacer(
                                        modifier = Modifier.height(10.dp)
                                    )

                                    Text(
                                        text = "Nenhum evento encontrado",
                                        color = EventsText,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "Tente mudar a busca ou os filtros.",
                                        color = EventsMuted,
                                        fontSize = 13.sp
                                    )
                                }
                            }

                        } else {

                            eventosFiltrados.forEach { evento ->

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            abrirDetalhes(
                                                id = evento.id,
                                                nome = evento.nome,
                                                data = evento.data,
                                                horario = evento.horario,
                                                local = evento.local,
                                                descricao = evento.descricao
                                            )
                                        },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 2.dp
                                    )
                                ) {

                                    Column(
                                        modifier = Modifier.padding(17.dp)
                                    ) {

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Box(
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .clip(
                                                        RoundedCornerShape(15.dp)
                                                    )
                                                    .background(
                                                        Color(0xFFEAF0FF)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {

                                                Text(
                                                    text = if (
                                                        evento.categoria == "Acadêmico"
                                                    ) {
                                                        "🎓"
                                                    } else {
                                                        "💻"
                                                    },
                                                    fontSize = 22.sp
                                                )
                                            }

                                            Spacer(
                                                modifier = Modifier.width(12.dp)
                                            )

                                            Column(
                                                modifier = Modifier.weight(1f)
                                            ) {

                                                Text(
                                                    text = evento.nome,
                                                    color = EventsText,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                )

                                                Spacer(
                                                    modifier = Modifier.height(5.dp)
                                                )

                                                Text(
                                                    text = evento.categoria,
                                                    color = EventsBlue,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }

                                        Spacer(
                                            modifier = Modifier.height(16.dp)
                                        )

                                        Text(
                                            text = "📅  ${evento.data}  •  ${evento.horario}",
                                            color = EventsText,
                                            fontSize = 12.sp
                                        )

                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )

                                        Text(
                                            text = "📍  ${evento.local}",
                                            color = EventsMuted,
                                            fontSize = 12.sp
                                        )

                                        Spacer(
                                            modifier = Modifier.height(13.dp)
                                        )

                                        Text(
                                            text = if (
                                                eventoEncerrado(
                                                    evento.data,
                                                    evento.horario
                                                )
                                            ) {
                                                "VER DETALHES  →  •  ENCERRADO"
                                            } else {
                                                "VER DETALHES  →"
                                            },
                                            color = EventsBlue,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.height(13.dp)
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Button(
                            onClick = {
                                finish()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(51.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EventsNavy,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "VOLTAR",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "CAMPUSHUB • URI SANTIAGO",
                            color = EventsMuted,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
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