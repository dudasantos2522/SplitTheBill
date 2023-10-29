package br.edu.scl.ifsp.ads.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.adapter.IntegranteAdapter
import br.edu.scl.ifsp.ads.splitthebill.controller.IntegranteController
import br.edu.scl.ifsp.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.EXTRA_CALCULAR
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.EXTRA_INTEGRANTE
import br.edu.scl.ifsp.ads.splitthebill.model.Constant.VIEW_INTEGRANTE
import br.edu.scl.ifsp.ads.splitthebill.model.Integrante

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val integranteList: MutableList<Integrante> = mutableListOf()

    // Controller
    private val integranteController: IntegranteController by lazy {
        IntegranteController(this)
    }

    // Adapter
    private val integranteAdapter: IntegranteAdapter by lazy {
        IntegranteAdapter(this, integranteList)
    }

    private lateinit var arl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.toolbarIn.toolbar)
        amb.integrantesLv.adapter = integranteAdapter
        arl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK) {
                val integrante = result.data?.getParcelableExtra<Integrante>(EXTRA_INTEGRANTE)
                integrante?.let{_integrante ->
                    if (integranteList.any { it.id == _integrante.id }) {
                        integranteController.editIntegrante(_integrante)
                    } else {
                        integranteController.insertIntegrante(_integrante)
                    }
                }
            }
        }

        amb.integrantesLv.setOnItemClickListener { parent, view, position, id ->
            val integrante = integranteList[position]
            val viewIntegranteIntent = Intent(this, IntegranteActivity::class.java)
            viewIntegranteIntent.putExtra(EXTRA_INTEGRANTE, integrante)
            viewIntegranteIntent.putExtra(VIEW_INTEGRANTE, true)
            startActivity(viewIntegranteIntent)
        }

        registerForContextMenu(amb.integrantesLv)
        integranteController.getIntegrantes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addIntegranteMi -> {
                arl.launch(Intent(this, IntegranteActivity::class.java))
                true
            }
            R.id.calcularRachaMi -> {
                val calcularRachaIntent = Intent(this, RacharActivity::class.java)
                calcularRachaIntent.putExtra(EXTRA_CALCULAR, ArrayList<Integrante>(integranteList))
                arl.launch(calcularRachaIntent)
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu( menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val integrante = integranteList[position]
        return when (item.itemId) {
            R.id.removeIntegranteMi -> {
                integranteController.removeIntegrante(integrante)
                Toast.makeText(this, "Integrante removido", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editIntegranteMi -> {
                val editIntegranteIntent = Intent(this, IntegranteActivity::class.java)
                editIntegranteIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                arl.launch(editIntegranteIntent)
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.integrantesLv)
    }

    fun updateIntegranteList(_integranteList: MutableList<Integrante>) {
        integranteList.clear()
        integranteList.addAll(_integranteList)
        integranteAdapter.notifyDataSetChanged()
    }
}