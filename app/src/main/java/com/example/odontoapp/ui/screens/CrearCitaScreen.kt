package com.example.odontoapp.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.odontoapp.model.CitaRequest
import com.example.odontoapp.model.OdontologoRequest
import com.example.odontoapp.model.PacienteRequest
import com.example.odontoapp.viewmodel.CitaViewModel

@Composable
fun CrearCitaScreen(viewModel: CitaViewModel, onCitaCreated: () -> Unit) {
    var pacienteId by remember { mutableStateOf("") }
    var odontologoId by remember { mutableStateOf("") }
    var tratamiento by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var pagado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = pacienteId,
            onValueChange = { pacienteId = it },
            label = { Text("ID del Paciente") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = odontologoId,
            onValueChange = { odontologoId = it },
            label = { Text("ID del Odontólogo") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = tratamiento,
            onValueChange = { tratamiento = it },
            label = { Text("Tratamiento") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (YYYY-MM-DDTHH:MM:SSZ)") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(
                checked = pagado,
                onCheckedChange = { pagado = it }
            )
            Text("Pagado")
        }

        Button(onClick = {
            val citaRequest = CitaRequest(
                paciente = PacienteRequest(id = pacienteId.toInt()),
                odontologo = OdontologoRequest(id = odontologoId.toInt()),
                tratamiento = tratamiento,
                descripcion = descripcion,
                fecha = fecha,
                valor = valor.toDouble(),
                pagado = pagado
            )
            viewModel.createCita(
                citaRequest = citaRequest,
                onSuccess = { onCitaCreated() },
                onError = { it.printStackTrace() }
            )
        }) {
            Text("Registrar Cita")
        }
    }
}
