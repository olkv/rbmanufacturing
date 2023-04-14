package com.example.rbmanufacturing.presentation.otk.defect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.COtkItems

class DefectFragmentAdapter(context: Context, val callback: (rowid: Int?)-> Unit): RecyclerView.Adapter<DefectFragmentAdapter.ViewHolder>() {
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_items = mutableListOf<COtkItems>()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        //val txtNameNom = view.findViewById<TextView>(R.id.txtNameNom)

        fun bind(item: COtkItems) {

            //txtNameNom.text = item.name +" "+item.parameter

            itemView.setOnClickListener {
                callback(adapterPosition)
            }

        }

    }

    internal fun setDocOtk(items: MutableList<COtkItems>) {
        this.t_items = items
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefectFragmentAdapter.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.rowitem_editdefect,parent,false))
    }

    override fun onBindViewHolder(holder: DefectFragmentAdapter.ViewHolder, position: Int) {
        val item = t_items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return t_items.size
    }


}