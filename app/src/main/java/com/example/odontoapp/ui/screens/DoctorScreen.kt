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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.sp
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Encabezado
        Text(
            text = "ODONTÓLOGOS",
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

            IconButton(onClick = { /* Acción para agregar odontólogo */ }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Agregar Odontólogo",
                    tint = Color(0xFF7B8AFF),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Mensaje de carga
        if (odontologos.isEmpty()) {
            Text(
                text = "Cargando odontólogos...",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            // Lista de odontólogos (filtrada o completa)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 80.dp) // Pa que no se vea maluco la parte de abajo
            ) {
                items(filteredOdontologos) { odontologo ->
                    OdontologoCard(odontologo)
                }
            }
        }
    }
}

@Composable
fun OdontologoCard(odontologo: Odontologo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del odontólogo
            Image(
                painter = painterResource(id = R.drawable.odontologo_image), // Usa una imagen por defecto o desde el Backend
                contentDescription = "Foto de ${odontologo.nombre}",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información del odontólogo
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Dr. ${odontologo.nombre}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
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
