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
import com.google.firebase.auth.FirebaseAuth

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
                        .padding(30.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    verticalArrangement = Arrangement.Center
                ) {

                    Text("BEM-VINDO AO CAMPUSHUB!")

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    EventsActivity::class.java
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("EVENTOS")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    MyEventsActivity::class.java
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("MEUS EVENTOS")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    ProfileActivity::class.java
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("MEU PERFIL")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            auth.signOut()

                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    MainActivity::class.java
                                )
                            )

                            finish()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SAIR")
                    }
                }
            }
        }
    }
}