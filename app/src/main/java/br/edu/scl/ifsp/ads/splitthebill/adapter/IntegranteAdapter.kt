package br.edu.scl.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.splitthebill.R
import br.edu.scl.ifsp.ads.splitthebill.databinding.TileIntegranteBinding
import br.edu.scl.ifsp.ads.splitthebill.model.Integrante

class IntegranteAdapter(context: Context, private val integranteList: MutableList<Integrante>):
    ArrayAdapter<Integrante>(context, R.layout.tile_integrante, integranteList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val integrante = integranteList[position]
        var tib: TileIntegranteBinding? = null

        var integranteTileView = convertView
        if(integranteTileView == null) {
            tib = TileIntegranteBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            integranteTileView = tib.root

            val tileIntegranteHolder = TileIntegranteHolder(tib.nomeTv, tib.pagouTv)
            integranteTileView.tag = tileIntegranteHolder
        }
        val holder = integranteTileView.tag as TileIntegranteHolder
        holder.nomeTv.setText(integrante.nome)
        holder.pagouTv.setText(integrante.pagou.toString())

        tib?.nomeTv?.setText(integrante.nome)
        tib?.pagouTv?.setText(integrante.pagou.toString())
        tib?.nomeTv?.text = integrante.nome
        tib?.pagouTv?.text = integrante.pagou.toString()

        return integranteTileView
    }

    private data class TileIntegranteHolder(val nomeTv: TextView, val pagouTv: TextView)
}