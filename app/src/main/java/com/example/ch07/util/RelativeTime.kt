package com.example.ch07.util

// TODO-Done [T3.3] Implementasikan fungsi murni (tanpa Android) ini. Selisih
//   `now - then` (negatif dianggap 0) diubah menjadi teks:
//     < 1 menit  -> "baru saja"        < 1 hari   -> "N jam lalu"
//     < 1 jam    -> "N menit lalu"     < 30 hari  -> "N hari lalu"
//     < 365 hari -> "N bulan lalu" (1 bulan = 30 hari)   selebihnya -> "N tahun lalu"
//   Tes yang sudah disediakan: RelativeTimeTest (merah sampai fungsi ini selesai).
fun formatRelativeTime(now: Long, then: Long): String {
    val dif = now-then
    val sec = dif/1_000
    val min = sec/60
    val hour = min/60
    val day = hour/24
    val month = day/30
    val year = day/365

    if (year>=1)
        return "$year tahun lalu"
    if (month>=1)
        return "$month bulan lalu"
    if (day>=1)
        return "$day hari lalu"
    if (hour>=1)
        return "$hour jam lalu"
    if (min>=1)
        return "$min menit lalu"
    return "Baru Saja"
}
