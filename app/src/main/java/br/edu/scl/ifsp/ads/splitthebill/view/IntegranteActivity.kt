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
                    pagouEt.isEnabled = false
                    comprouEt.isEnabled = false
                    salvarBt.visibility = View.GONE
                }
                nomeEt.setText(_receivedIntegrante.nome)
                pagouEt.setText(_receivedIntegrante.pagou.toString())
                comprouEt.setText(_receivedIntegrante.comprou)
            }
        }

        with(aib) {
            salvarBt.setOnClickListener {
                val integrante = Integrante(
                    id = receivedIntegrante?.id,
                    nome = nomeEt.text.toString(),
                    pagou = pagouEt.text.toString().toDouble(),
                    comprou = comprouEt.text.toString()
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}