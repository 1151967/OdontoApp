package com.example.odontoapp.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odontoapp.model.Cita
import com.example.odontoapp.viewmodel.CitaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CitasScreen(viewModel: CitaViewModel) {
    val citas by viewModel.citas.collectAsState()

    // Cargar las citas al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchCitas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Citas Programadas") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(citas) { cita ->
                CitaCard(cita)
            }
        }
    }
}

@Composable
fun CitaCard(cita: Cita) {
    val formattedDate = formatDateTime(cita.fecha)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Información del paciente
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = "Paciente", tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Paciente: ${cita.pacienteNombre ?: "Sin asignar"}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Información del odontólogo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = "Odontólogo", tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Odontólogo: ${cita.odontologoNombre}",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tratamiento
            Text(
                text = "Tratamiento: ${cita.tratamiento}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de la cita
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = "Fecha", tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Fecha: $formattedDate",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Estado de pago
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (cita.pagado) Icons.Default.CheckCircle else Icons.Default.AccountBox,
                    contentDescription = "Pagado",
                    tint = if (cita.pagado) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Pagado: ${if (cita.pagado) "Sí" else "No"}",
                    fontSize = 16.sp,
                    color = if (cita.pagado) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Función para formatear la fecha
fun formatDateTime(isoDate: String): String {
    return try {
        val dateTime = LocalDateTime.parse(isoDate, DateTimeFormatter.ISO_DATE_TIME)
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    } catch (e: Exception) {
        "Fecha inválida"
    }
}
