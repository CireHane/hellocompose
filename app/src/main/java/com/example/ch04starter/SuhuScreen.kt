package com.example.ch04starter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ch04starter.ui.theme.Ch04StarterTheme

// ============================================================================
// TODO Pertemuan 4 — SuhuScreen (soal #2 di Tugas Pertemuan 4)
//
// Kalkulator konversi suhu Celsius <-> Fahrenheit <-> Kelvin, memakai
// `derivedStateOf` — sama seperti pola `bmi` di BmiScreen, tapi di sini
// hasil derivasinya dipakai sebagai TAMPILAN saja (read-only), bukan input.
//
// Rumus yang perlu diimplementasikan:
//   F = C * 9/5 + 32
//   K = C + 273.15
//
// [ ] 2a. Field "Celsius" di bawah masih kosong logikanya — parse teks
//         input jadi Float (hati-hati input tidak valid / kosong!)
// [ ] 2b. Hitung `fahrenheit` dan `kelvin` dari `celsius` memakai
//         `derivedStateOf`, lalu tampilkan di kedua Text di bawah field
// [ ] (Tantangan, opsional) Jadikan ketiga field bisa DIKETIK bebas —
//         Fahrenheit atau Kelvin pun boleh jadi sumber input. Hati-hati:
//         kalau ketiganya saling mengisi satu sama lain secara langsung,
//         bisa terjadi UPDATE MELINGKAR (circular update). Coba pikirkan
//         cara melacak "field mana yang sedang aktif diketik" sebagai
//         satu-satunya sumber kebenaran (single source of truth, ingat
//         slide UDF), baru dua field lain murni derived dari situ.
// ============================================================================

@Composable
fun SuhuScreen() {
    var inputFocus by remember { mutableIntStateOf(0) }
    var textFocus by remember { mutableStateOf("0") }
    var numFocus by remember { mutableFloatStateOf(0f) }

    var celsiusText by remember { mutableStateOf("0.0") }
    fun handleCelsius(){
        if(inputFocus == 2){
            celsiusText = "%.1f".format((numFocus-32) *5/9)
        }
        else if(inputFocus == 3){
            celsiusText = "%.1f".format(numFocus - 273.15)
        }
    }

    var farText by remember { mutableStateOf("32.0") }
    fun handleFahrenheit(){
        if(inputFocus == 1){
            farText = "%.1f".format(numFocus * 9/5 + 32)
        }
        else if(inputFocus == 3){
            farText = "%.1f".format((numFocus - 273.15) * 9/5 + 32)
        }
    }

    var kelvText by remember { mutableStateOf("273.15") }
    fun handleKelv(){
        if(inputFocus == 1){
            kelvText = "%.1f".format(numFocus + 273.15)
        }
        else if(inputFocus == 2){
            kelvText = "%.1f".format((numFocus-32) * 5 / 9 + 273.15)
        }
    }

    fun handleAll(text : String){
        textFocus = text
        numFocus = textFocus.toFloatOrNull() ?: 0f
        handleCelsius()
        handleFahrenheit()
        handleKelv()
    }
//    val kelvin by remember(celsius) { derivedStateOf { celsius + 273.15 } }

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text  = "Konversi Suhu",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value         = celsiusText,
            onValueChange = {
                if(inputFocus == 1){
                    celsiusText = it
                    handleAll(it)
                }
            },
            label         = { Text("Celsius (°C)") },
            maxLines      = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier      = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused && inputFocus != 1) {
                        textFocus = farText
                        inputFocus = 1
                    }
                    else if (inputFocus == 1){
                        inputFocus = 0
                    }
                }
        )

        // Fahrenhait Text
        OutlinedTextField(
            value         = farText,
            onValueChange = {
                if(inputFocus == 2){
                    farText = it
                    handleAll(it)
                }
            },
            label         = { Text("Fahrenheit (°F)") },
            maxLines      = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier      = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused && inputFocus != 2) {
                        textFocus = farText
                        inputFocus = 2
                    }
                    else if (inputFocus == 2){
                        inputFocus = 0
                    }
                }
        )

        OutlinedTextField(
            value         = kelvText,
            onValueChange = {
                if(inputFocus == 3){
                    kelvText = it
                    handleAll(it)
                }
            },
            label         = { Text("Kelvin (°K)") },
            maxLines      = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier      = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (state.isFocused && inputFocus != 3) {
                        textFocus = kelvText
                        inputFocus = 3
                    }
                }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuhuScreenPreview() {
    Ch04StarterTheme { SuhuScreen() }
}
