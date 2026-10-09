package br.com.loginfirebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth

private val CampusNavy = Color(0xFF172554)
private val CampusBlue = Color(0xFF3568D4)
private val CampusBackground = Color(0xFFF3F6FC)
private val CampusText = Color(0xFF24324A)
private val CampusMuted = Color(0xFF718096)

class DashboardActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            LoginFirebaseTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CampusBackground)
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
                                    bottomStart = 32.dp,
                                    bottomEnd = 32.dp
                                )
                            )
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        CampusNavy,
                                        CampusBlue
                                    )
                                )
                            )
                            .padding(
                                horizontal = 24.dp,
                                vertical = 26.dp
                            )
                    ) {

                        Column {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(
                                            RoundedCornerShape(16.dp)
                                        )
                                        .background(
                                            Color.White.copy(alpha = 0.17f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = "CH",
                                        color = Color.White,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.size(13.dp)
                                )

                                Column {

                                    Text(
                                        text = "CampusHub",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "ÁREA DO ALUNO",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 11.sp,
                                        letterSpacing = 1.5.sp
                                    )
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(28.dp)
                            )

                            Text(
                                text = "Bem-vindo ao CampusHub!",
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = "Seu espaço acadêmico em um só lugar.",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Menu principal
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 22.dp,
                                vertical = 24.dp
                            )
                    ) {

                        Text(
                            text = "O que você deseja fazer?",
                            color = CampusText,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "Escolha uma das opções abaixo.",
                            color = CampusMuted,
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(22.dp)
                        )

                        // EVENTOS
                        OpcaoDashboard(
                            icone = "📅",
                            titulo = "Eventos",
                            descricao = "Explore os eventos disponíveis",
                            destaque = true,
                            aoClicar = {
                                startActivity(
                                    Intent(
                                        this@DashboardActivity,
                                        EventsActivity::class.java
                                    )
                                )
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(13.dp)
                        )

                        // MEUS EVENTOS
                        OpcaoDashboard(
                            icone = "🎓",
                            titulo = "Meus Eventos",
                            descricao = "Veja suas inscrições",
                            aoClicar = {
                                startActivity(
                                    Intent(
                                        this@DashboardActivity,
                                        MyEventsActivity::class.java
                                    )
                                )
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(13.dp)
                        )

                        // MEUS FAVORITOS
                        OpcaoDashboard(
                            icone = "⭐",
                            titulo = "Meus Favoritos",
                            descricao = "Acesse seus eventos favoritos",
                            aoClicar = {
                                startActivity(
                                    Intent(
                                        this@DashboardActivity,
                                        FavoritesActivity::class.java
                                    )
                                )
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(13.dp)
                        )

                        // MEU PERFIL
                        OpcaoDashboard(
                            icone = "👤",
                            titulo = "Meu Perfil",
                            descricao = "Consulte seus dados pessoais",
                            aoClicar = {
                                startActivity(
                                    Intent(
                                        this@DashboardActivity,
                                        ProfileActivity::class.java
                                    )
                                )
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(13.dp)
                        )

                        // SAIR
                        OpcaoDashboard(
                            icone = "↪",
                            titulo = "Sair",
                            descricao = "Encerrar sua sessão",
                            sair = true,
                            aoClicar = {
                                auth.signOut()

                                startActivity(
                                    Intent(
                                        this@DashboardActivity,
                                        MainActivity::class.java
                                    )
                                )

                                finish()
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "CAMPUSHUB • URI SANTIAGO",
                            color = CampusMuted,
                            fontSize = 11.sp,
                            letterSpacing = 1.2.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OpcaoDashboard(
    icone: String,
    titulo: String,
    descricao: String,
    destaque: Boolean = false,
    sair: Boolean = false,
    aoClicar: () -> Unit
) {

    val corFundo = when {
        destaque -> CampusBlue
        sair -> Color(0xFFFFEEEE)
        else -> Color.White
    }

    val corTexto = when {
        destaque -> Color.White
        sair -> Color(0xFFB42318)
        else -> CampusText
    }

    val corDescricao = when {
        destaque -> Color.White.copy(alpha = 0.82f)
        sair -> Color(0xFFB42318).copy(alpha = 0.8f)
        else -> CampusMuted
    }

    Button(
        onClick = aoClicar,
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp),
        shape = RoundedCornerShape(19.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = corFundo,
            contentColor = corTexto
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (destaque) 3.dp else 1.dp,
            pressedElevation = 4.dp
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (destaque) {
                            Color.White.copy(alpha = 0.17f)
                        } else if (sair) {
                            Color(0xFFFADADA)
                        } else {
                            Color(0xFFEAF0FF)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = icone,
                    fontSize = 20.sp
                )
            }

            Spacer(
                modifier = Modifier.size(13.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = titulo,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = corTexto
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = descricao,
                    fontSize = 11.sp,
                    color = corDescricao
                )
            }

            Text(
                text = "›",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                color = corTexto
            )
        }
    }
}