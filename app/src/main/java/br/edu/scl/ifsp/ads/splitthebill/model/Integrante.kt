package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity
data class Integrante(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @NonNull
    var nome: String,
    var pagou: Double,
    var comprou: String,
): Parcelable