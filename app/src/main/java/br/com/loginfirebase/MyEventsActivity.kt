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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.loginfirebase.ui.theme.LoginFirebaseTheme

class MyEventsActivity : ComponentActivity() {

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

                    Text("MEUS EVENTOS")

                    Spacer(modifier = Modifier.height(30.dp))

                    Text("Semana Acadêmica")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("20/09/2026 - 19:00")

                    Spacer(modifier = Modifier.height(25.dp))

                    Text("Hackathon CampusHub")

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("25/09/2026 - 08:00")

                    Spacer(modifier = Modifier.height(30.dp))

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