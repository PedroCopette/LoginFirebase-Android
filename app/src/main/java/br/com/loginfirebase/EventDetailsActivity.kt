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
import androidx.compose.material3.OutlinedTextField
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
    private var favorito by mutableStateOf(false)

    private var comentarios by mutableStateOf<List<Comentario>>(emptyList())
    private var textoComentario by mutableStateOf("")
    private var comentarioEditandoId by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idEvento = intent.getStringExtra("idEvento") ?: ""
        val nomeEvento = intent.getStringExtra("nomeEvento") ?: "Evento"
        val dataEvento = intent.getStringExtra("dataEvento") ?: "Data não informada"
        val horarioEvento = intent.getStringExtra("horarioEvento")
            ?: "Horário não informado"
        val localEvento = intent.getStringExtra("localEvento")
            ?: "Local não informado"
        val descricaoEvento = intent.getStringExtra("descricaoEvento")
            ?: "Descrição não informada"

        verificarInscricao(idEvento)
        verificarFavorito(idEvento)
        carregarComentarios(idEvento)

        setContent {
            LoginFirebaseTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp)
                        .verticalScroll(rememberScrollState()),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Top
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (favorito) {
                            Text("★ REMOVER DOS FAVORITOS")
                        } else {
                            Text("☆ ADICIONAR AOS FAVORITOS")
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text("COMENTÁRIOS")

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = textoComentario,
                        onValueChange = {
                            textoComentario = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                if (comentarioEditandoId == null)
                                    "Digite seu comentário"
                                else
                                    "Editando comentário"
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            if (comentarioEditandoId == null) {
                                publicarComentario(
                                    idEvento,
                                    nomeEvento
                                )
                            } else {
                                editarComentario()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (comentarioEditandoId == null) {
                            Text("PUBLICAR COMENTÁRIO")
                        } else {
                            Text("SALVAR EDIÇÃO")
                        }
                    }

                    if (comentarioEditandoId != null) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                textoComentario = ""
                                comentarioEditandoId = null
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("CANCELAR EDIÇÃO")
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    if (comentarios.isEmpty()) {

                        Text("Ainda não existem comentários.")

                    } else {

                        comentarios.forEach { comentario ->

                            Text("Autor: ${comentario.autorNome}")

                            Spacer(modifier = Modifier.height(5.dp))

                            Text("Data: ${comentario.dataPublicacao}")

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(comentario.texto)

                            if (comentario.usuarioId == auth.currentUser?.uid) {

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        textoComentario = comentario.texto
                                        comentarioEditandoId = comentario.id
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("EDITAR")
                                }

                                Spacer(modifier = Modifier.height(5.dp))

                                Button(
                                    onClick = {
                                        excluirComentario(comentario.id)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("EXCLUIR")
                                }
                            }

                            Spacer(modifier = Modifier.height(25.dp))
                        }
                    }

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

    private fun verificarFavorito(idEvento: String) {

        val usuario = auth.currentUser ?: return

        val idFavorito = "${usuario.uid}_$idEvento"

        db.collection("favoritos")
            .document(idFavorito)
            .get()
            .addOnSuccessListener { documento ->
                favorito = documento.exists()
            }
    }

    private fun adicionarFavorito(
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
                "Faça login para favoritar eventos.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val idFavorito = "${usuario.uid}_$idEvento"

        val dados = hashMapOf(
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

    private fun removerFavorito(idEvento: String) {

        val usuario = auth.currentUser ?: return

        val idFavorito = "${usuario.uid}_$idEvento"

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

    private fun publicarComentario(
        idEvento: String,
        nomeEvento: String
    ) {

        val usuario = auth.currentUser

        if (usuario == null) {
            Toast.makeText(
                this,
                "Faça login para comentar.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val texto = textoComentario.trim()

        if (texto.isEmpty()) {
            Toast.makeText(
                this,
                "Digite um comentário.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val comentarioId = db.collection("comentarios").document().id

        val autorNome = usuario.displayName
            ?: usuario.email
            ?: "Usuário"

        val dataAtual = java.text.SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            java.util.Locale.getDefault()
        ).format(java.util.Date())

        val dados = hashMapOf(
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

    private fun carregarComentarios(idEvento: String) {

        db.collection("comentarios")
            .whereEqualTo("idEvento", idEvento)
            .get()
            .addOnSuccessListener { resultado ->

                val lista = resultado.documents.mapNotNull { documento ->

                    val id = documento.id
                    val texto = documento.getString("texto")
                    val usuarioId = documento.getString("usuarioId")
                    val autorNome = documento.getString("autorNome")
                    val dataPublicacao = documento.getString("dataPublicacao")

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

        val idComentario = comentarioEditandoId ?: return

        val texto = textoComentario.trim()

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
            .update("texto", texto)
            .addOnSuccessListener {

                textoComentario = ""
                comentarioEditandoId = null

                Toast.makeText(
                    this,
                    "Comentário atualizado!",
                    Toast.LENGTH_SHORT
                ).show()

                val idEvento = intent.getStringExtra("idEvento") ?: ""
                carregarComentarios(idEvento)
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao editar comentário.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun excluirComentario(idComentario: String) {

        db.collection("comentarios")
            .document(idComentario)
            .delete()
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Comentário excluído!",
                    Toast.LENGTH_SHORT
                ).show()

                val idEvento = intent.getStringExtra("idEvento") ?: ""
                carregarComentarios(idEvento)
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao excluir comentário.",
                    Toast.LENGTH_SHORT
                ).show()
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

data class Comentario(
    val id: String,
    val texto: String,
    val usuarioId: String,
    val autorNome: String,
    val dataPublicacao: String
)