package com.example.odontoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.odontoapp.R

@Composable
fun ListaOpciones(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // Color de fondo azul claro
            .padding(16.dp)
    ) {
        // Nombre de usuario con icono
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person, // Icono de perfil
                contentDescription = "Perfil",
                modifier = Modifier.size(32.dp),
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Administrador",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Subrayado
        Divider(color = Color.Blue)

        Spacer(modifier = Modifier.height(16.dp))

        // Menú de opciones con imágenes desde drawable
        MenuItem(icon = painterResource(id = R.drawable.ic_money), text = "Caja") {}
        MenuItem(icon = painterResource(id = R.drawable.ic_home), text = "Home") {}
        MenuItem(icon = painterResource(id = R.drawable.ic_doctor), text = "Odontólogos") {
            navController.navigate("odontologos") // Navega a la pantalla de Odontólogos
        }
        MenuItem(icon = painterResource(id = R.drawable.ic_calendar), text = "Citas") {}
        MenuItem(icon = painterResource(id = R.drawable.ic_herramientas), text = "Herramientas") {}
        MenuItem(icon = painterResource(id = R.drawable.ic_lab), text = "Laboratorio") {}
    }
}

@Composable
fun MenuItem(icon: Painter, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp) // Aumenté el padding vertical para más espacio entre los ítems
            .clickable { onClick() } // Ejecuta la acción de navegación
    ) {
        // Cambié Icon por Image para usar Painter
        Image(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(40.dp), // Aumenté el tamaño del icono
            alignment = Alignment.Center,
        )
        Spacer(modifier = Modifier.width(24.dp)) // Aumenté el espacio entre el icono y el texto
        Text(
            text = text,
            color = Color.Black,
            fontSize = 20.sp, // Aumenté el tamaño de la fuente del texto
            fontWeight = FontWeight.Bold // Opcional: Puse el texto en negrita
        )
    }
}


