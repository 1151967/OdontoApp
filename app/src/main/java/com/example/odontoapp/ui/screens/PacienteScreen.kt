package com.example.odontoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.odontoapp.R
import com.example.odontoapp.model.Paciente
import com.example.odontoapp.repository.PacienteRepository
import com.example.odontoapp.viewmodel.PacienteViewModel
import com.example.odontoapp.viewmodel.PacienteViewModelFactory
import androidx.compose.ui.focus.FocusRequester
import com.example.odontoapp.model.Especialidad
import com.example.odontoapp.model.TipoPersona
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun PacienteScreen(
    viewModel: PacienteViewModel = viewModel(
        factory = PacienteViewModelFactory(
            PacienteRepository()
        )
    ),
    function: () -> Unit
) {
    val pacientes by viewModel.filteredPacientes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var mostrarFormulario by remember { mutableStateOf(false) }

    // Cargar los pacientes al iniciar la pantalla
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

        // Barra de búsqueda y botón agregar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("Buscar...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            )

            IconButton(onClick = { mostrarFormulario = true }) {
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

        // Mostrar formulario de agregar paciente
        if (mostrarFormulario) {
            Dialog(onDismissRequest = { mostrarFormulario = false }) {
                FormularioPaciente(
                    onGuardar = { nuevoPaciente ->
                        viewModel.agregarPaciente(nuevoPaciente)
                        mostrarFormulario = false
                    },
                    onCancelar = { mostrarFormulario = false }
                )
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
@Composable
fun FormularioPaciente(
    onGuardar: (Paciente) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var cedula by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Formato de fecha para mostrar y guardar
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Ajustar el calendario con la fecha seleccionada
            calendar.set(year, month, dayOfMonth)

            // Formatear la fecha seleccionada
            fechaNacimiento = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Registro de Paciente", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cedula,
                onValueChange = { cedula = it },
                label = { Text("Cedula") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { },
                label = { Text("Fecha de Nacimiento") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Seleccionar fecha",
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onCancelar,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        val nuevoPaciente = Paciente(
                            id = 0,
                            nombre = nombre,
                            cedula = cedula,
                            direccion = direccion,
                            gmail = email,
                            fechaNacimiento = fechaNacimiento, // Ahora será en formato "yyyy-MM-dd"
                            tipopersona = TipoPersona(
                                id = 2,
                                descripcion = "Paciente"
                            ),
                            especialidad = Especialidad(
                                id = 2,
                                descripcion = "General"
                            ),
                            serviciosRecibidos = null
                        )
                        onGuardar(nuevoPaciente)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B8AFF))
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}