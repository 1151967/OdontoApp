package com.example.odontoapp.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.odontoapp.ui.theme.OdontoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OdontoAppTheme {
                // Crear el NavController para la navegación
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainScreen(
                        onMenuClick = { navController.navigate("listaOpciones") },
                        onProfileClick = { /* Acción para el perfil */ },
                        modifier = Modifier.padding(innerPadding),
                        navController = navController // Pasa el navController aquí
                    )
                }
            }
        }
    }
}



