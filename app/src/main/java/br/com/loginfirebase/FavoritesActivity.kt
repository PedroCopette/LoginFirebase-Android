package br.com.loginfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class FavoritoEvento(
    val id: String,
    val nome: String,
    val data: String,
    val horario: String,
    val local: String
)

private val FavoritesNavy = Color(0xFF172554)
private val FavoritesBlue = Color(0xFF3568D4)
private val FavoritesBackground = Color(0xFFF3F6FC)
private val FavoritesText = Color(0xFF24324A)
private val FavoritesMuted = Color(0xFF718096)

class FavoritesActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var eventosFavoritos by mutableStateOf<List<FavoritoEvento>>(
        emptyList()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginFirebaseTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(FavoritesBackground)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .verticalScroll(rememberScrollState())
                ) {

                    // Cabeçalho do CampusHub
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
                                        FavoritesNavy,
                                        FavoritesBlue
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
                                        text = "ÁREA DO ALUNO",
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
                                text = "Meus Favoritos",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Guarde os eventos que você quer acompanhar.",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Conteúdo principal
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {

                        Spacer(
                            modifier = Modifier.height(22.dp)
                        )

                        Text(
                            text = "Eventos salvos",
                            color = FavoritesText,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = if (eventosFavoritos.isEmpty()) {
                                "Seus eventos favoritos aparecerão aqui."
                            } else {
                                "${eventosFavoritos.size} evento(s) favorito(s)"
                            },
                            color = FavoritesMuted,
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        if (eventosFavoritos.isEmpty()) {

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(22.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(27.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(
                                                RoundedCornerShape(23.dp)
                                            )
                                            .background(Color(0xFFEAF0FF)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "⭐",
                                            fontSize = 34.sp
                                        )
                                    }

                                    Spacer(
                                        modifier = Modifier.height(16.dp)
                                    )

                                    Text(
                                        text = "Nenhum favorito ainda",
                                        color = FavoritesText,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    Text(
                                        text = "Quando você favoritar um evento, ele aparecerá nesta tela.",
                                        color = FavoritesMuted,
                                        fontSize = 13.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                        } else {

                            eventosFavoritos.forEach { evento ->

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(21.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 3.dp
                                    )
                                ) {

                                    Column(
                                        modifier = Modifier.padding(18.dp)
                                    ) {

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            Box(
                                                modifier = Modifier
                                                    .size(50.dp)
                                                    .clip(
                                                        RoundedCornerShape(15.dp)
                                                    )
                                                    .background(Color(0xFFEAF0FF)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "⭐",
                                                    fontSize = 25.sp
                                                )
                                            }

                                            Spacer(
                                                modifier = Modifier.width(13.dp)
                                            )

                                            Column(
                                                modifier = Modifier.weight(1f)
                                            ) {

                                                Text(
                                                    text = evento.nome,
                                                    color = FavoritesText,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold
                                                )

                                                Spacer(
                                                    modifier = Modifier.height(5.dp)
                                                )

                                                Text(
                                                    text = "NOS SEUS FAVORITOS",
                                                    color = FavoritesBlue,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    letterSpacing = 0.5.sp
                                                )
                                            }
                                        }

                                        Spacer(
                                            modifier = Modifier.height(17.dp)
                                        )

                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(
                                                    RoundedCornerShape(13.dp)
                                                )
                                                .background(Color(0xFFF3F6FC))
                                                .padding(12.dp)
                                        ) {

                                            Text(
                                                text = "Data e horário",
                                                color = FavoritesMuted,
                                                fontSize = 11.sp
                                            )

                                            Spacer(
                                                modifier = Modifier.height(5.dp)
                                            )

                                            Text(
                                                text = "${evento.data}  •  ${evento.horario}",
                                                color = FavoritesText,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }

                                        Spacer(
                                            modifier = Modifier.height(12.dp)
                                        )

                                        Row(
                                            verticalAlignment = Alignment.Top
                                        ) {

                                            Text(
                                                text = "📍",
                                                fontSize = 15.sp
                                            )

                                            Spacer(
                                                modifier = Modifier.width(8.dp)
                                            )

                                            Text(
                                                text = evento.local,
                                                color = FavoritesMuted,
                                                fontSize = 13.sp
                                            )
                                        }

                                        Spacer(
                                            modifier = Modifier.height(18.dp)
                                        )

                                        Button(
                                            onClick = {
                                                removerFavorito(evento.id)
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp),
                                            shape = RoundedCornerShape(13.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFFFEEEE),
                                                contentColor = Color(0xFFB42318)
                                            )
                                        ) {
                                            Text(
                                                text = "REMOVER DOS FAVORITOS",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.height(15.dp)
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            onClick = {
                                finish()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = FavoritesNavy,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "VOLTAR",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "CAMPUSHUB • URI SANTIAGO",
                            color = FavoritesMuted,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        carregarFavoritos()
    }

    private fun carregarFavoritos() {

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

        db.collection("favoritos")
            .whereEqualTo("usuarioId", usuario.uid)
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull { documento ->

                    val id = documento.getString("idEvento")
                    val nome = documento.getString("nomeEvento")
                    val data = documento.getString("dataEvento")
                    val horario = documento.getString("horarioEvento")
                    val local = documento.getString("localEvento")

                    if (
                        id != null &&
                        nome != null &&
                        data != null &&
                        horario != null &&
                        local != null
                    ) {

                        FavoritoEvento(
                            id = id,
                            nome = nome,
                            data = data,
                            horario = horario,
                            local = local
                        )

                    } else {
                        null
                    }
                }

                eventosFavoritos = lista
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao carregar seus favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun removerFavorito(idEvento: String) {

        val usuario = auth.currentUser ?: return
        val idFavorito = "${usuario.uid}_$idEvento"

        db.collection("favoritos")
            .document(idFavorito)
            .delete()
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Evento removido dos favoritos!",
                    Toast.LENGTH_SHORT
                ).show()

                carregarFavoritos()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao remover dos favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}