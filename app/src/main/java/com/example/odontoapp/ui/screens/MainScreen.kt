package com.example.odontoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odontoapp.R


@Composable
fun MainScreen(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Header
        AppHeader(onMenuClick = onMenuClick, onProfileClick = onProfileClick)

        // Servicios section
        ServiciosSection()

        // Lista de citas
        CitasList()
    }
}



@Composable
fun AppHeader(onMenuClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMenuClick() }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(text = "RP", style = MaterialTheme.typography.titleLarge, fontSize = 20.sp)

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { onProfileClick() }) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile"
            )
        }
    }
}

@Composable
fun ServiciosSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD3E1FF))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Servicios",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Blue // Puedes ajustar el color según tus necesidades
                )
                Text(
                    text = "servicios",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun CitasList() {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(5) { index ->
            CitaItem(
                title = if (index == 2) "Esterilización" else "Cita",
                subtitle = if (index == 2) "esterilizar instrumentos" else "Leonardo Lobo",
                doctor = if (index == 2) "DR. VALENTINA IRRESPONSABLE" else "DR. ENRIQUE"
            )
        }
    }
}

@Composable
fun CitaItem(title: String, subtitle: String, doctor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Cita",
            modifier = Modifier.size(40.dp),
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontSize = 16.sp)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(text = doctor, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        IconButton(onClick = { /* Acciones */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options"
            )
        }
    }
}