package com.example.ch03

import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.bringIntoViewResponder
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch03.ui.theme.Ch03Theme

data class Mahasiswa(val nama: String, val nim: String, val ipk: Double)

val dummyMahasiswa = listOf(
    Mahasiswa("Eric Shane", "01082240008", 3.0),
    Mahasiswa("Ali Rahman", "22001", 3.85),
    Mahasiswa("Budi Santoso", "22002", 3.40),
    Mahasiswa("Cici Wulandari", "22003", 3.92),
    Mahasiswa("Dian Pratama", "22004", 2.95),
    Mahasiswa("Eka Fitriani", "22005", 3.75),
    Mahasiswa("Fandi Ahmad", "22006", 3.50),
    Mahasiswa("Gita Permata", "22007", 3.88),
    Mahasiswa("Hendra Kusuma", "22008", 2.80),
    Mahasiswa("Indah Lestari", "22009", 3.65),
    Mahasiswa("Jana Lana", "22010", 3.20),
    Mahasiswa("Eka Reka", "22011", 3.75),
    Mahasiswa("Andi Fandi", "22012", 3.50),
    Mahasiswa("Gita Rita", "22013", 3.88),
    Mahasiswa("Hendra Candra", "22014", 2.80),
    Mahasiswa("Indah Pindah", "22015", 3.65),
)


@Composable
fun StudentListScreen() {
    DaftarMahasiswa(dummyMahasiswa)
}

@Composable
fun DaftarMahasiswa(mahasiswaList: List<Mahasiswa>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Nama & NIM")
                Text("IPK")
            }
        }
        items(
            mahasiswaList,
            key = {it.nim}
        ) { mahasiswa ->
            MahasiswaCard(mahasiswa)
        }
        item{
                Text(
                    text = "End of the List...",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f)
                )
        }
    }
}

@Composable
fun MahasiswaCard(mahasiswa: Mahasiswa) {
    Card (
        Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        LazyRow (
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                Column {
                    Text(
                        text = mahasiswa.nama,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = mahasiswa.nim,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                Text(
                    text = "IPK ${mahasiswa.ipk}",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (mahasiswa.ipk >= 3.5) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StudentListScreenPreview() {
    Ch03Theme {
        StudentListScreen()
    }
}
