package com.example.ch10

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// TODO-DONE [T1.1] Tandai class ini dengan @HiltAndroidApp, lalu hapus properti
//  `container` dan isi onCreate(). Hilt yang akan merakit dependency.
@HiltAndroidApp
class MainApplication : Application()
