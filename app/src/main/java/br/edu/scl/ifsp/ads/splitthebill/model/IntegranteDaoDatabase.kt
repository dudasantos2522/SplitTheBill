package br.edu.scl.ifsp.ads.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Integrante::class], version = 1)
abstract class IntegranteDaoDatabase: RoomDatabase() {
    abstract fun getIntegranteDao(): IntegranteDao
}