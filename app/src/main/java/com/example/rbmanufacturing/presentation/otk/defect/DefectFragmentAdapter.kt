package com.example.rbmanufacturing.presentation.otk.defect

import android.content.Context
import android.graphics.Color
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

        val txtVidDefect = view.findViewById<TextView>(R.id.txtVidDefect)
        val txtTypeDefect = view.findViewById<TextView>(R.id.txtTypeDefect)
        val txtDescriptionDefect = view.findViewById<TextView>(R.id.txtDescriptionDefect)
        val txtCountDefect = view.findViewById<TextView>(R.id.txtCountDefect)

        fun bind(item: COtkItems) {

            txtVidDefect.text = item.vid_defect
            txtTypeDefect.text = item.type_defect
            txtDescriptionDefect.text = item.description

            if(item.type_defect=="Устранимый") {
                txtTypeDefect.setTextColor(Color.rgb(0x1a,0x97,0xf6))
            } else {
                txtTypeDefect.setTextColor(Color.rgb(0xfa,0x4d,0x64))
            }

            val count = item.count

            txtCountDefect.text = "Количество выявленных дефектов : $count шт."

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