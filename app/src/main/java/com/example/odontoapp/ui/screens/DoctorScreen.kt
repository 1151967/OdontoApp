import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.odontoapp.model.Odontologo
import com.example.odontoapp.viewmodel.OdontologoViewModel
import com.example.odontoapp.R
import com.example.odontoapp.ui.screens.AppHeader


@Composable
fun OdontologosScreen(viewModel: OdontologoViewModel) {
    val odontologos by viewModel.odontologos.collectAsState() // Lista completa de odontólogos
    val searchQuery by viewModel.searchQuery.collectAsState() // Valor de la barra de búsqueda

    // Llamar a fetchOdontologos() al iniciar la pantalla para cargar los odontólogos
    LaunchedEffect(Unit) {
        viewModel.fetchOdontologos()
    }

    // Filtrar los odontólogos según la búsqueda
    val filteredOdontologos = viewModel.filteredOdontologos()

    // Verificar si hay odontólogos para mostrar
    if (odontologos.isEmpty()) {
        Text("Cargando odontólogos...", style = MaterialTheme.typography.bodyMedium)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Encabezado de la pantalla
        Text(
            text = "ODONTOLOGOS",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF4F6FD1),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Barra de búsqueda y botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) }, // Actualiza la consulta de búsqueda
                placeholder = { Text("Buscar....") },
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            FloatingActionButton(onClick = { /* Acción para agregar */ }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de odontólogos (filtrada o completa)
        LazyColumn {
            items(filteredOdontologos) { odontologo ->
                OdontologoCard(odontologo)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}



@Composable
fun OdontologoCard(odontologo: Odontologo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del odontólogo
        Image(
            painter = painterResource(id = R.drawable.odontologo_image), // Reemplazar  con una imagen real del Backend
            contentDescription = "Foto de ${odontologo.nombre}",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Información del odontólogo
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Dr. ${odontologo.nombre}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

        }

        Spacer(modifier = Modifier.width(8.dp))

        // Botón de edición
        IconButton(onClick = { /* Acción para editar */ }) {
            Icon(
                painter = painterResource(id = R.drawable.edit_icon), // Usa el icono de edición correspondiente
                contentDescription = "Editar",
                tint = Color(0xFF9C8DF7), // Color del icono
                modifier = Modifier
                    .background(Color(0xFFECE6FF), shape = RectangleShape)// Fondo cuadrado con bordes redondeados
                    .size(48.dp) // Tamaño del botón
                    .padding(8.dp) // Espaciado interno para ajustar el icono
            )
        }



    }
}
