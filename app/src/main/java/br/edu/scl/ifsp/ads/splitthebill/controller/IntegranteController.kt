package br.edu.scl.ifsp.ads.splitthebill.controller

import androidx.room.Room
import br.edu.scl.ifsp.ads.splitthebill.model.Integrante
import br.edu.scl.ifsp.ads.splitthebill.model.IntegranteDao
import br.edu.scl.ifsp.ads.splitthebill.model.IntegranteDao.Companion.INTEGRANTE_DATABASE_FILE
import br.edu.scl.ifsp.ads.splitthebill.model.IntegranteDaoDatabase
import br.edu.scl.ifsp.ads.splitthebill.view.MainActivity

class IntegranteController(private val mainActivity: MainActivity) {
    private val integranteDaoImpl: IntegranteDao by lazy {
        Room.databaseBuilder(mainActivity, IntegranteDaoDatabase::class.java, INTEGRANTE_DATABASE_FILE)
            .build().getIntegranteDao()
    }

    fun insertIntegrante(integrante: Integrante) {
        Thread {
            integranteDaoImpl.createIntegrante(integrante)
            getIntegrantes()
        }.start()
    }

    fun getIntegrante(id: Int) = integranteDaoImpl.retrieveIntegrante(id)

    fun getIntegrantes() {
        Thread {
            val integrantes = integranteDaoImpl.retrieveIntegrantes()
            mainActivity.runOnUiThread {
                mainActivity.updateIntegranteList(integrantes)
            }
        }.start()
    }

    fun editIntegrante(integrante: Integrante) {
        Thread {
            integranteDaoImpl.updateIntegrante(integrante)
            getIntegrantes()
        }.start()
    }
    fun removeIntegrante(integrante: Integrante) {
        Thread {
            integranteDaoImpl.deleteIntegrante(integrante)
            getIntegrantes()
        }.start()
    }
}