package com.example.rbmanufacturing.presentation.otk.documents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.COtkDocument
import com.example.rbmanufacturing.domain.repository.RowClickListiner

class OtkDocumentsAdapter(context: Context, val rowClickListiner: RowClickListiner): RecyclerView.Adapter<OtkDocumentsAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_items =  mutableListOf<COtkDocument>()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtNumberDoc = view.findViewById<TextView>(R.id.txtNumberDoc)
        val txtDateDoc = view.findViewById<TextView>(R.id.txtDateDoc)
        val txtDescriptionDoc = view.findViewById<TextView>(R.id.txtDescriptionDoc)
        val txtDepartmantDoc = view.findViewById<TextView>(R.id.txtDepartmantDoc)
        val txtTypeDoc = view.findViewById<TextView>(R.id.txtTypeDoc)

        fun bind(item: COtkDocument) {

            txtTypeDoc.text = item.type_doc
            txtNumberDoc.text = item.number.toInt().toString()
            txtDateDoc.text = "от "+item.date
            txtDescriptionDoc.text = item.description
            txtDepartmantDoc.text = item.department

            itemView.setOnClickListener {
                rowClickListiner.OnClick(adapterPosition)
            }

        }

    }

    internal fun setItemsOtkDocuments(items: MutableList<COtkDocument>) {
        this.t_items = items
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtkDocumentsAdapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_document_otk,parent,false))
    }

    override fun onBindViewHolder(holder: OtkDocumentsAdapter.ViewHolder, position: Int) {
        var item = t_items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return t_items.size
    }


}