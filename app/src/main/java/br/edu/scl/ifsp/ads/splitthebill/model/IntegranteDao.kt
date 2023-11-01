package br.edu.scl.ifsp.ads.splitthebill.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface IntegranteDao {
    companion object {
        const val INTEGRANTE_DATABASE_FILE = "integrantes_room"
        private const val INTEGRANTE_TABLE = "integrante"
        private const val ID_COLUMN = "id"
    }

    @Insert
    fun createIntegrante(integrante: Integrante)
    @Query("SELECT * FROM $INTEGRANTE_TABLE WHERE $ID_COLUMN = :id")
    fun retrieveIntegrante(id: Int): Integrante?
    @Query("SELECT * FROM $INTEGRANTE_TABLE")
    fun retrieveIntegrantes(): MutableList<Integrante>
    @Update
    fun updateIntegrante(integrante: Integrante): Int
    @Delete
    fun deleteIntegrante(integrante: Integrante): Int
}