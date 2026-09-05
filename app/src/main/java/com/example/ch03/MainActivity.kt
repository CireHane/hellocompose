package com.example.ch03

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ch03.ui.theme.Ch03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ch03Theme {
                // TODO 1: Ganti StarterScreen dengan MainScreen dari README.
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen() {
    // TODO 2: Buat MainScreen dengan Scaffold, TopAppBar, dan NavigationBar.
    var activeTab : Int by remember { mutableIntStateOf(0) } // 0 -> PROFLIE TAB | 1 -> ALL MAHASISWA LIST TAB
    val title = if(activeTab == 0) "Proflie" else "Daftar Mahasiswa"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        },
        bottomBar = {
            NavigationBar{
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Daftar Mahasiswa") }
                )
            }
        }
    ) { pad ->
        Box(Modifier.padding(pad)){
            when(activeTab){
                0 -> ProfileScreen()
                1 -> StudentListScreen()
            }
        }
    }
}
