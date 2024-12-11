package com.example.odontoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.repository.PacienteRepository
import com.example.odontoapp.viewmodel.PacienteViewModel
import com.example.odontoapp.viewmodel.PacienteViewModelFactory
import com.example.odontoapp.R

@Composable
fun PacienteScreen(viewModel: PacienteViewModel = viewModel(factory = PacienteViewModelFactory(
    PacienteRepository()
))) {
    val pacientes by viewModel.pacientes.collectAsState()

    // Pa llamar a fetch pacientes cuando se carga esa activity
    LaunchedEffect(Unit) {
        viewModel.fetchPacientes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Encabezado
        Text(
            text = "PACIENTES",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7B8AFF),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Barra de buscar y botón agregar, falta arreglar para que funcione esa mierda
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar...") },
                leadingIcon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            )

            IconButton(onClick = { /* Acción para agregar paciente */ }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Agregar Paciente",
                    tint = Color(0xFF7B8AFF),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Lista de pacientes
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pacientes) { paciente ->
                PacienteCard(paciente)
            }
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del paciente
            Image(
                painter = painterResource(id = R.drawable.odontologo_image), // Usa una imagen por defecto
                contentDescription = "Foto de ${paciente.nombre}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información del paciente
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = paciente.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "tratamiento",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "citas: 4",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Botón de edición
            IconButton(
                onClick = { /* Acción para editar */ },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color(0xFFB39DDB)
                )
            }
        }
    }
}
