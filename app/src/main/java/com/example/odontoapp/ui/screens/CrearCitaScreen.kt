package com.example.odontoapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.odontoapp.model.CitaRequest
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.model.OdontologoRequest
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.model.PacienteRequest
import com.example.odontoapp.viewmodel.CitaViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CrearCitaScreen(viewModel: CitaViewModel, onCitaCreated: () -> Unit) {
    val context = LocalContext.current

    // Estados locales para el formulario
    var selectedPaciente by remember { mutableStateOf<Paciente?>(null) }
    var selectedOdontologo by remember { mutableStateOf<Odontologo?>(null) }
    var tratamiento by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var pagado by remember { mutableStateOf(false) }

    // Obtener listas desde el ViewModel
    val pacientes by viewModel.pacientes.collectAsState()
    val odontologos by viewModel.odontologos.collectAsState()

    // Cargar datos al iniciar
    LaunchedEffect(Unit) {
        viewModel.fetchPacientes()
        viewModel.fetchOdontologos()
    }

    // Formato para la fecha y hora
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Selección de paciente
        var pacienteDropdownExpanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { pacienteDropdownExpanded = true }) {
                Text(selectedPaciente?.nombre ?: "Seleccionar Paciente")
            }
            DropdownMenu(
                expanded = pacienteDropdownExpanded,
                onDismissRequest = { pacienteDropdownExpanded = false }
            ) {
                pacientes.forEach { paciente ->
                    DropdownMenuItem(onClick = {
                        selectedPaciente = paciente
                        pacienteDropdownExpanded = false
                    }) {
                        Text(paciente.nombre)
                    }
                }
            }
        }

        // Selección de odontólogo
        var odontologoDropdownExpanded by remember { mutableStateOf(false) }
        Box {
            Button(onClick = { odontologoDropdownExpanded = true }) {
                Text(selectedOdontologo?.nombre ?: "Seleccionar Odontólogo")
            }
            DropdownMenu(
                expanded = odontologoDropdownExpanded,
                onDismissRequest = { odontologoDropdownExpanded = false }
            ) {
                odontologos.forEach { odontologo ->
                    DropdownMenuItem(onClick = {
                        selectedOdontologo = odontologo
                        odontologoDropdownExpanded = false
                    }) {
                        Text(odontologo.nombre)
                    }
                }
            }
        }

        // Selección de fecha
        Button(onClick = {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    fecha = dateFormatter.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text(if (fecha.isNotEmpty()) "Fecha: $fecha" else "Seleccionar Fecha")
        }

        // Selección de hora
        Button(onClick = {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    hora = timeFormatter.format(calendar.time)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }) {
            Text(if (hora.isNotEmpty()) "Hora: $hora" else "Seleccionar Hora")
        }

        // Otros campos del formulario
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
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = pagado,
                onCheckedChange = { pagado = it }
            )
            Text("Pagado")
        }

        // Botón para registrar la cita
        Button(
            onClick = {
                if (selectedPaciente != null && selectedOdontologo != null && fecha.isNotEmpty() && hora.isNotEmpty()) {
                    val citaRequest = CitaRequest(
                        paciente = PacienteRequest(id = selectedPaciente!!.id),
                        odontologo = OdontologoRequest(id = selectedOdontologo!!.id),
                        tratamiento = tratamiento,
                        descripcion = descripcion,
                        fecha = "${fecha}T${hora}Z",
                        valor = valor.toDoubleOrNull() ?: 0.0,
                        pagado = pagado
                    )
                    viewModel.createCita(
                        citaRequest = citaRequest,
                        onSuccess = { onCitaCreated() },
                        onError = { it.printStackTrace() }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Cita")
        }
    }
}
