package br.edu.scl.ifsp.ads.splitthebill.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.adapter.IntegranteAdapter
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityRacharBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Constant
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.EXTRA_CALCULAR
import br.edu.scl.ifsp.ads.splitthebill.model.Integrante

class RacharActivity : AppCompatActivity() {
    private val arb: ActivityRacharBinding by lazy {
        ActivityRacharBinding.inflate(layoutInflater)
    }

    private val integranteList: MutableList<Integrante> = mutableListOf()

    private val integranteAdpter: IntegranteAdapter by lazy {
        IntegranteAdapter(this, integranteList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(arb.root)

        setSupportActionBar(arb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Rachar conta"

        val receivedIntegrante = intent.getParcelableArrayListExtra<Integrante>(EXTRA_CALCULAR)

        receivedIntegrante?.let {
            updateList(it, calcular(it))
            arb.integrantesLv.adapter = integranteAdpter
        }
    }

    fun updateList(integrantesList: ArrayList<Integrante>, valor: Double) {
        integranteList.clear()
        for (integrante in integrantesList) {
            integrante.pagou = integrante.pagou - valor
            if (integrante.pagou < 0) {
                integrante.nome = integrante.nome.plus(" - pagar")
            } else if (integrante.pagou > 0) {
                integrante.nome = integrante.nome.plus(" - receber")
            } else {
                integrante.nome = integrante.nome.plus(" - zerado")
            }
            integrante.pagou = Math.abs(integrante.pagou)
        }

        integranteList.addAll(integrantesList)
        integranteAdpter.notifyDataSetChanged()
    }

    private fun calcular(integranteList: ArrayList<Integrante>): Double {
        var totalConta = 0.0

        for (integrante in integranteList) {
            totalConta += integrante.pagou
        }

        return totalConta / integranteList.size
    }
}