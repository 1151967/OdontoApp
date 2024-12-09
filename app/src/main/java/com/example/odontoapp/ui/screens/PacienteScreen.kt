package com.example.odontoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.repository.PacienteRepository
import com.example.odontoapp.viewmodel.PacienteViewModel
import com.example.odontoapp.viewmodel.PacienteViewModelFactory

@Composable
fun PacienteScreen(viewModel: PacienteViewModel = viewModel(factory = PacienteViewModelFactory(
    PacienteRepository()
))) {
    val pacientes by viewModel.pacientes.collectAsState()

    // Llama al método fetchPacientes cuando se carga la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchPacientes()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pacientes) { paciente ->
            PacienteCard(paciente)
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${paciente.nombre}", fontSize = 16.sp)
            Text(text = "Cédula: ${paciente.cedula}", fontSize = 14.sp)
            Text(text = "Fecha de Nacimiento: ${paciente.fechaNacimiento}", fontSize = 14.sp)
            Text(text = "Email: ${paciente.gmail ?: "No disponible"}", fontSize = 14.sp)
        }
    }
}