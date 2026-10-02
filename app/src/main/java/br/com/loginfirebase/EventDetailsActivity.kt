package br.com.loginfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class EventDetailsActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var inscrito by mutableStateOf(false)
    private var favorito by mutableStateOf(false)

    private var comentarios by mutableStateOf<List<Comentario>>(emptyList())
    private var textoComentario by mutableStateOf("")
    private var comentarioEditandoId by mutableStateOf<String?>(null)

    private var notaSelecionada by mutableStateOf(0)
    private var notaUsuario by mutableStateOf(0)
    private var mediaAvaliacoes by mutableStateOf(0.0)
    private var quantidadeAvaliacoes by mutableStateOf(0)

    private var eventoEncerrado by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idEvento = intent.getStringExtra("idEvento") ?: ""
        val nomeEvento = intent.getStringExtra("nomeEvento") ?: "Evento"
        val dataEvento = intent.getStringExtra("dataEvento")
            ?: "Data não informada"
        val horarioEvento = intent.getStringExtra("horarioEvento")
            ?: "Horário não informado"
        val localEvento = intent.getStringExtra("localEvento")
            ?: "Local não informado"
        val descricaoEvento = intent.getStringExtra("descricaoEvento")
            ?: "Descrição não informada"

        verificarInscricao(idEvento)
        verificarFavorito(idEvento)
        carregarComentarios(idEvento)
        verificarEventoEncerrado(dataEvento, horarioEvento)
        carregarAvaliacaoUsuario(idEvento)
        carregarMediaAvaliacoes(idEvento)

        setContent {
            LoginFirebaseTheme {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Detalhes do Evento",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                TextButton(
                                    onClick = {
                                        finish()
                                    }
                                ) {
                                    Text(
                                        text = "←",
                                        fontSize = 30.sp
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 24.dp)
                            .verticalScroll(
                                rememberScrollState()
                            ),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        // =========================
                        // NOME DO EVENTO
                        // =========================

                        Text(
                            text = nomeEvento,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        // =========================
                        // DATA
                        // =========================

                        Text(
                            text = "Data",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = dataEvento,
                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        // =========================
                        // HORÁRIO
                        // =========================

                        Text(
                            text = "Horário",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = horarioEvento,
                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        // =========================
                        // LOCAL
                        // =========================

                        Text(
                            text = "Local",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = localEvento,
                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        HorizontalDivider()

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        // =========================
                        // DESCRIÇÃO
                        // =========================

                        Text(
                            text = descricaoEvento,
                            fontSize = 17.sp,
                            lineHeight = 25.sp
                        )

                        Spacer(
                            modifier = Modifier.height(25.dp)
                        )

                        // =========================
                        // INSCRIÇÃO
                        // =========================

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
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text(
                                text = if (inscrito)
                                    "CANCELAR INSCRIÇÃO"
                                else
                                    "INSCREVER-SE"
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        // =========================
                        // FAVORITOS
                        // =========================

                        OutlinedButton(
                            onClick = {

                                if (favorito) {

                                    removerFavorito(idEvento)

                                } else {

                                    adicionarFavorito(
                                        idEvento,
                                        nomeEvento,
                                        dataEvento,
                                        horarioEvento,
                                        localEvento
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text(
                                text = if (favorito)
                                    "★  REMOVER DOS FAVORITOS"
                                else
                                    "☆  ADICIONAR AOS FAVORITOS"
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(30.dp)
                        )

                        HorizontalDivider()

                        Spacer(
                            modifier = Modifier.height(25.dp)
                        )

                        // =========================
                        // AVALIAÇÃO
                        // =========================

                        Text(
                            text = "Avaliação do evento",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        if (mediaAvaliacoes > 0) {

                            Text(
                                text = "Média %.1f ⭐  •  %d avaliações"
                                    .format(
                                        mediaAvaliacoes,
                                        quantidadeAvaliacoes
                                    ),
                                fontSize = 16.sp
                            )

                        } else {

                            Text(
                                text = "Ainda não existem avaliações.",
                                fontSize = 16.sp
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        // =========================
                        // USUÁRIO PODE AVALIAR
                        // =========================

                        if (eventoEncerrado && inscrito) {

                            Text(
                                text = "Sua avaliação",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Text(
                                text = if (notaSelecionada == 0)
                                    "Escolha uma nota"
                                else
                                    "Nota selecionada: $notaSelecionada ⭐",
                                fontSize = 15.sp
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            // =========================
                            // ESTRELAS
                            // =========================

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                for (i in 1..5) {

                                    TextButton(
                                        onClick = {
                                            notaSelecionada = i
                                        }
                                    ) {

                                        Text(
                                            text = if (
                                                i <= notaSelecionada
                                            ) {
                                                "★"
                                            } else {
                                                "☆"
                                            },
                                            fontSize = 34.sp
                                        )
                                    }
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Button(
                                onClick = {
                                    salvarAvaliacao(idEvento)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {

                                Text(
                                    text = if (notaUsuario == 0)
                                        "AVALIAR EVENTO"
                                    else
                                        "ALTERAR AVALIAÇÃO"
                                )
                            }

                        } else if (!eventoEncerrado) {

                            Text(
                                text = "A avaliação estará disponível após o encerramento do evento.",
                                fontSize = 15.sp
                            )

                        } else {

                            Text(
                                text = "Somente alunos inscritos podem avaliar este evento.",
                                fontSize = 15.sp
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(30.dp)
                        )

                        HorizontalDivider()

                        Spacer(
                            modifier = Modifier.height(25.dp)
                        )

                        // =========================
                        // COMENTÁRIOS
                        // =========================

                        Text(
                            text = "Comentários",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        OutlinedTextField(
                            value = textoComentario,
                            onValueChange = {
                                textoComentario = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {

                                Text(
                                    text = if (
                                        comentarioEditandoId == null
                                    ) {
                                        "Digite seu comentário"
                                    } else {
                                        "Editando comentário"
                                    }
                                )
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Button(
                            onClick = {

                                if (
                                    comentarioEditandoId == null
                                ) {

                                    publicarComentario(
                                        idEvento,
                                        nomeEvento
                                    )

                                } else {

                                    editarComentario()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text(
                                text = if (
                                    comentarioEditandoId == null
                                ) {
                                    "PUBLICAR COMENTÁRIO"
                                } else {
                                    "SALVAR EDIÇÃO"
                                }
                            )
                        }

                        if (
                            comentarioEditandoId != null
                        ) {

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            TextButton(
                                onClick = {
                                    textoComentario = ""
                                    comentarioEditandoId = null
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = "CANCELAR EDIÇÃO"
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        if (comentarios.isEmpty()) {

                            Text(
                                text = "Ainda não existem comentários.",
                                fontSize = 15.sp
                            )

                        } else {

                            comentarios.forEach { comentario ->

                                Text(
                                    text = "Autor: ${comentario.autorNome}",
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = "Data: ${comentario.dataPublicacao}",
                                    fontSize = 13.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(6.dp)
                                )

                                Text(
                                    text = comentario.texto,
                                    fontSize = 16.sp
                                )

                                if (
                                    comentario.usuarioId ==
                                    auth.currentUser?.uid
                                ) {

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                        TextButton(
                                            onClick = {

                                                textoComentario =
                                                    comentario.texto

                                                comentarioEditandoId =
                                                    comentario.id
                                            }
                                        ) {

                                            Text(
                                                text = "EDITAR"
                                            )
                                        }

                                        Spacer(
                                            modifier = Modifier.width(8.dp)
                                        )

                                        TextButton(
                                            onClick = {

                                                excluirComentario(
                                                    comentario.id
                                                )
                                            }
                                        ) {

                                            Text(
                                                text = "EXCLUIR"
                                            )
                                        }
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.height(20.dp)
                                )

                                HorizontalDivider()

                                Spacer(
                                    modifier = Modifier.height(20.dp)
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
        }
    }

    // =====================================================
    // INSCRIÇÃO
    // =====================================================

    private fun verificarInscricao(
        idEvento: String
    ) {

        val usuario =
            auth.currentUser ?: return

        val idInscricao =
            "${usuario.uid}_$idEvento"

        db.collection("inscricoes")
            .document(idInscricao)
            .get()
            .addOnSuccessListener { documento ->

                inscrito =
                    documento.exists()
            }
    }

    private fun fazerInscricao(
        idEvento: String,
        nomeEvento: String,
        dataEvento: String,
        horarioEvento: String,
        localEvento: String
    ) {

        val usuario =
            auth.currentUser

        if (usuario == null) {

            Toast.makeText(
                this,
                "Faça login para se inscrever.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val idInscricao =
            "${usuario.uid}_$idEvento"

        val dados =
            hashMapOf(
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

    private fun cancelarInscricao(
        idEvento: String
    ) {

        val usuario =
            auth.currentUser ?: return

        val idInscricao =
            "${usuario.uid}_$idEvento"

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

    // =====================================================
    // FAVORITOS
    // =====================================================

    private fun verificarFavorito(
        idEvento: String
    ) {

        val usuario =
            auth.currentUser ?: return

        val idFavorito =
            "${usuario.uid}_$idEvento"

        db.collection("favoritos")
            .document(idFavorito)
            .get()
            .addOnSuccessListener { documento ->

                favorito =
                    documento.exists()
            }
    }

    private fun adicionarFavorito(
        idEvento: String,
        nomeEvento: String,
        dataEvento: String,
        horarioEvento: String,
        localEvento: String
    ) {

        val usuario =
            auth.currentUser

        if (usuario == null) {

            Toast.makeText(
                this,
                "Faça login para favoritar eventos.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val idFavorito =
            "${usuario.uid}_$idEvento"

        val dados =
            hashMapOf(
                "idEvento" to idEvento,
                "nomeEvento" to nomeEvento,
                "dataEvento" to dataEvento,
                "horarioEvento" to horarioEvento,
                "localEvento" to localEvento,
                "usuarioId" to usuario.uid,
                "usuarioEmail" to usuario.email
            )

        db.collection("favoritos")
            .document(idFavorito)
            .set(dados)
            .addOnSuccessListener {

                favorito = true

                Toast.makeText(
                    this,
                    "Evento adicionado aos favoritos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao adicionar aos favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun removerFavorito(
        idEvento: String
    ) {

        val usuario =
            auth.currentUser ?: return

        val idFavorito =
            "${usuario.uid}_$idEvento"

        db.collection("favoritos")
            .document(idFavorito)
            .delete()
            .addOnSuccessListener {

                favorito = false

                Toast.makeText(
                    this,
                    "Evento removido dos favoritos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao remover dos favoritos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // =====================================================
    // COMENTÁRIOS
    // =====================================================

    private fun publicarComentario(
        idEvento: String,
        nomeEvento: String
    ) {

        val usuario =
            auth.currentUser

        if (usuario == null) {

            Toast.makeText(
                this,
                "Faça login para comentar.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val texto =
            textoComentario.trim()

        if (texto.isEmpty()) {

            Toast.makeText(
                this,
                "Digite um comentário.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val comentarioId =
            db.collection("comentarios")
                .document()
                .id

        val autorNome =
            usuario.displayName
                ?: usuario.email
                ?: "Usuário"

        val dataAtual =
            SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
            ).format(Date())

        val dados =
            hashMapOf(
                "idEvento" to idEvento,
                "nomeEvento" to nomeEvento,
                "texto" to texto,
                "usuarioId" to usuario.uid,
                "autorNome" to autorNome,
                "dataPublicacao" to dataAtual
            )

        db.collection("comentarios")
            .document(comentarioId)
            .set(dados)
            .addOnSuccessListener {

                textoComentario = ""

                Toast.makeText(
                    this,
                    "Comentário publicado!",
                    Toast.LENGTH_SHORT
                ).show()

                carregarComentarios(idEvento)
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao publicar comentário.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun carregarComentarios(
        idEvento: String
    ) {

        db.collection("comentarios")
            .whereEqualTo(
                "idEvento",
                idEvento
            )
            .get()
            .addOnSuccessListener { resultado ->

                val lista =
                    resultado.documents.mapNotNull { documento ->

                        val id =
                            documento.id

                        val texto =
                            documento.getString(
                                "texto"
                            )

                        val usuarioId =
                            documento.getString(
                                "usuarioId"
                            )

                        val autorNome =
                            documento.getString(
                                "autorNome"
                            )

                        val dataPublicacao =
                            documento.getString(
                                "dataPublicacao"
                            )

                        if (
                            texto != null &&
                            usuarioId != null &&
                            autorNome != null &&
                            dataPublicacao != null
                        ) {

                            Comentario(
                                id = id,
                                texto = texto,
                                usuarioId = usuarioId,
                                autorNome = autorNome,
                                dataPublicacao = dataPublicacao
                            )

                        } else {

                            null
                        }
                    }

                comentarios = lista
            }
    }

    private fun editarComentario() {

        val idComentario =
            comentarioEditandoId ?: return

        val texto =
            textoComentario.trim()

        if (texto.isEmpty()) {

            Toast.makeText(
                this,
                "Digite um comentário.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        db.collection("comentarios")
            .document(idComentario)
            .update(
                "texto",
                texto
            )
            .addOnSuccessListener {

                textoComentario = ""
                comentarioEditandoId = null

                Toast.makeText(
                    this,
                    "Comentário atualizado!",
                    Toast.LENGTH_SHORT
                ).show()

                val idEvento =
                    intent.getStringExtra(
                        "idEvento"
                    ) ?: ""

                carregarComentarios(
                    idEvento
                )
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao editar comentário.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun excluirComentario(
        idComentario: String
    ) {

        db.collection("comentarios")
            .document(idComentario)
            .delete()
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Comentário excluído!",
                    Toast.LENGTH_SHORT
                ).show()

                val idEvento =
                    intent.getStringExtra(
                        "idEvento"
                    ) ?: ""

                carregarComentarios(
                    idEvento
                )
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao excluir comentário.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // =====================================================
    // AVALIAÇÕES
    // =====================================================

    private fun verificarEventoEncerrado(
        dataEvento: String,
        horarioEvento: String
    ) {

        try {

            val formato =
                SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
                )

            val dataEventoCompleta =
                "$dataEvento $horarioEvento"

            val data =
                formato.parse(
                    dataEventoCompleta
                )

            if (data != null) {

                eventoEncerrado =
                    Date().after(data)
            }

        } catch (e: Exception) {

            eventoEncerrado = false
        }
    }

    private fun carregarAvaliacaoUsuario(
        idEvento: String
    ) {

        val usuario =
            auth.currentUser ?: return

        val idAvaliacao =
            "${usuario.uid}_$idEvento"

        db.collection("avaliacoes")
            .document(idAvaliacao)
            .get()
            .addOnSuccessListener { documento ->

                if (documento.exists()) {

                    val nota =
                        documento
                            .getLong("nota")
                            ?.toInt()
                            ?: 0

                    notaUsuario = nota
                    notaSelecionada = nota
                }
            }
    }

    private fun salvarAvaliacao(
        idEvento: String
    ) {

        val usuario =
            auth.currentUser

        if (usuario == null) {

            Toast.makeText(
                this,
                "Faça login para avaliar.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (!inscrito) {

            Toast.makeText(
                this,
                "Você precisa estar inscrito no evento.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (!eventoEncerrado) {

            Toast.makeText(
                this,
                "O evento ainda não foi encerrado.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (notaSelecionada !in 1..5) {

            Toast.makeText(
                this,
                "Selecione uma nota de 1 a 5.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val idAvaliacao =
            "${usuario.uid}_$idEvento"

        val dados =
            hashMapOf(
                "idEvento" to idEvento,
                "usuarioId" to usuario.uid,
                "usuarioEmail" to usuario.email,
                "nota" to notaSelecionada
            )

        db.collection("avaliacoes")
            .document(idAvaliacao)
            .set(dados)
            .addOnSuccessListener {

                notaUsuario =
                    notaSelecionada

                Toast.makeText(
                    this,
                    "Avaliação salva!",
                    Toast.LENGTH_SHORT
                ).show()

                carregarMediaAvaliacoes(
                    idEvento
                )
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao salvar avaliação.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun carregarMediaAvaliacoes(
        idEvento: String
    ) {

        db.collection("avaliacoes")
            .whereEqualTo(
                "idEvento",
                idEvento
            )
            .get()
            .addOnSuccessListener { resultado ->

                if (resultado.isEmpty) {

                    mediaAvaliacoes = 0.0
                    quantidadeAvaliacoes = 0

                    return@addOnSuccessListener
                }

                val notas =
                    resultado.documents.mapNotNull {

                        it.getLong(
                            "nota"
                        )?.toInt()
                    }

                if (notas.isNotEmpty()) {

                    mediaAvaliacoes =
                        notas.average()

                    quantidadeAvaliacoes =
                        notas.size
                }
            }
    }
}

data class Comentario(
    val id: String,
    val texto: String,
    val usuarioId: String,
    val autorNome: String,
    val dataPublicacao: String
)