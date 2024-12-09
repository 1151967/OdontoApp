package com.example.odontoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odontoapp.model.Cita
import com.example.odontoapp.viewmodel.CitaViewModel

@Composable
fun CitasScreen(viewModel: CitaViewModel) {
    val citas by viewModel.citas.collectAsState()

    // Cargar las citas al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchCitas()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(citas) { cita ->
            CitaCard(cita)
        }
    }
}

@Composable
fun CitaCard(cita: Cita) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Paciente: ${cita.pacienteNombre ?: "Sin asignar"}", fontSize = 16.sp)
            Text(text = "Edad: ${if (cita.pacienteEdad > 0) cita.pacienteEdad else "N/A"}", fontSize = 14.sp)
            Text(text = "Odontólogo: ${cita.odontologoNombre}", fontSize = 14.sp)
            Text(text = "Tratamiento: ${cita.tratamiento}", fontSize = 14.sp)
            Text(text = "Fecha: ${cita.fecha}", fontSize = 14.sp)
            Text(text = "Pagado: ${if (cita.pagado) "Sí" else "No"}", fontSize = 14.sp)
        }
    }
}