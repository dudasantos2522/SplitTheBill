package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityIntegranteBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.EXTRA_INTEGRANTE
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.VIEW_INTEGRANTE
import br.edu.scl.ifsp.ads.splitthebill.model.Integrante

class IntegranteActivity : AppCompatActivity() {
    private val aib: ActivityIntegranteBinding by lazy {
        ActivityIntegranteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aib.root)

        setSupportActionBar(aib.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Detalhes do integrante"

        val receivedIntegrante = intent.getParcelableExtra<Integrante>(EXTRA_INTEGRANTE)
        receivedIntegrante?.let{_receivedIntegrante ->
            val viewIntegrante = intent.getBooleanExtra(VIEW_INTEGRANTE, false)
            with(aib) {
                if (viewIntegrante) {
                    nomeEt.isEnabled = false
                    descricaoItem1Et.isEnabled = false
                    valorItem1Et.isEnabled = false
                    descricaoItem2Et.isEnabled = false
                    valorItem2Et.isEnabled = false
                    descricaoItem3Et.isEnabled = false
                    valorItem3Et.isEnabled = false
                    salvarBt.visibility = View.GONE
                }
                nomeEt.setText(_receivedIntegrante.nome)
                descricaoItem1Et.setText(_receivedIntegrante.descricaoItem1)
                valorItem1Et.setText(_receivedIntegrante.valorItem1.toString())
                descricaoItem2Et.setText(_receivedIntegrante.descricaoItem2)
                valorItem2Et.setText(_receivedIntegrante.valorItem2.toString())
                descricaoItem3Et.setText(_receivedIntegrante.descricaoItem3)
                valorItem3Et.setText(_receivedIntegrante.valorItem3.toString())
            }
        }

        with(aib) {
            salvarBt.setOnClickListener {
                val valorItem1F =  valorItem1Et.text.toString().toDoubleOrNull()
                val valorItem2F =  valorItem2Et.text.toString().toDoubleOrNull()
                val valorItem3F =  valorItem3Et.text.toString().toDoubleOrNull()

                var total: Double = 0.0
                if (descricaoItem1Et.text.toString() != "") {
                    total += (valorItem1F?:0.00)
                }
                if (descricaoItem2Et.text.toString() != "") {
                    total += (valorItem2F?:0.00)
                }
                if (descricaoItem3Et.text.toString() != "") {
                    total += (valorItem3F?:0.00)
                }

                val integrante = Integrante(
                    id = receivedIntegrante?.id,
                    nome = nomeEt.text.toString(),
                    pagou = total,
                    descricaoItem1 = descricaoItem1Et.text.toString(),
                    valorItem1 = valorItem1F?:0.00,
                    descricaoItem2 = descricaoItem2Et.text.toString(),
                    valorItem2 = valorItem2F?:0.00,
                    descricaoItem3 = descricaoItem3Et.text.toString(),
                    valorItem3 = valorItem3F?:0.00
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}