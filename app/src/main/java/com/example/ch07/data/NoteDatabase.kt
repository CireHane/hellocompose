package com.example.ch07.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// TODO [T4.3] Buat `MIGRATION_1_2 = object : Migration(1, 2)` yang menjalankan
//   ALTER TABLE notes ADD COLUMN tags TEXT NOT NULL DEFAULT '[]'
//   ('[]' = daftar tag kosong, supaya catatan lama tetap terbaca).
// TODO-DONE [T3.2] Buat Migration berikutnya untuk kolom `updatedAt`
//   (INTEGER NOT NULL DEFAULT 0), lalu isi catatan lama dengan
//   UPDATE notes SET updatedAt = createdAt  agar tidak tampil "puluhan tahun lalu".
//   Nomor versi mengikuti URUTAN kamu mengerjakan Tugas 3 dan 4: selalu naik +1
//   dan `Migration(awal, akhir)` harus cocok. (Solusi ch07 mengerjakan tag dulu.)

val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD updatedAt INTEGER NOT NULL DEFAULT 0")
        db.execSQL("UPDATE notes SET updatedAt = createdAt")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD tags NOT NULL DEFAULT '[]'")
    }
}

@Database(
    entities = [NoteEntity::class],
    version = 3,                 // TODO [T3.2-Done][T4.3] naikkan setiap skema berubah
    exportSchema = true          // JSON skema -> app/schemas/ (lihat build.gradle.kts)
)
// TODO-DONE [T4.2] Daftarkan converter: @TypeConverters(Converters::class)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        // @Volatile: INSTANCE selalu dibaca dari memori utama, bukan cache thread
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        // synchronized: mencegah dua thread membuat instance bersamaan
        fun getInstance(context: Context): NoteDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    // TODO [T3.2-Done][T4.3-DONE] Ganti baris di bawah dengan
                    //   .addMigrations(MIGRATION_1_2, ...). fallbackToDestructiveMigration
                    //   MENGHAPUS data pengguna saat versi naik: hanya boleh saat
                    //   pengembangan awal, jangan dipakai di produksi.
//                    . fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
