package com.example.rbmanufacturing.presentation.otk.document

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.COtkDocItem

class OtkDocAdapter(context: Context, val callback: (rowid: Int?)-> Unit): RecyclerView.Adapter<OtkDocAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_items = mutableListOf<COtkDocItem>()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtNameNom = view.findViewById<TextView>(R.id.txtNameNom)
        val txtCount = view.findViewById<TextView>(R.id.txtCount)
        val txtCountDefectUst = view.findViewById<TextView>(R.id.txtCountDefectUst)
        val txtCountDefectNoUst = view.findViewById<TextView>(R.id.txtCountDefectNoUst)

        fun bind(item: COtkDocItem) {

            txtNameNom.text = item.name +" "+item.parameter
            txtCount.text = item.count.toString()
            txtCountDefectUst.text = item.countdefectust.toString()
            txtCountDefectNoUst.text = item.countdefectnoust.toString()

            itemView.setOnClickListener {
                callback(adapterPosition)
            }

        }

    }

    internal fun setDocOtk(items: MutableList<COtkDocItem>) {
        this.t_items = items
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtkDocAdapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.rowitem_docotk,parent,false))
    }

    override fun onBindViewHolder(holder: OtkDocAdapter.ViewHolder, position: Int) {
        val item = t_items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return t_items.size
    }


}