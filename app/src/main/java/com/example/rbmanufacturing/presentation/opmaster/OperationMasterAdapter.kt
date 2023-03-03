package com.example.rbmanufacturing.presentation.opmaster

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CItemOperationMaster
import com.example.rbmanufacturing.domain.repository.RowClickListiner

class OperationMasterAdapter(context: Context, val rowClickListiner: RowClickListiner): RecyclerView.Adapter<OperationMasterAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_items =  mutableListOf<CItemOperationMaster>()


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtNumberDoc = view.findViewById<TextView>(R.id.txtNumberDoc)
        val txtDateDoc = view.findViewById<TextView>(R.id.txtDateDoc)
        val txtDescriptionDoc = view.findViewById<TextView>(R.id.txtDescriptionDoc)
        val txtDepartmantDoc = view.findViewById<TextView>(R.id.txtDepartmantDoc)
        val crdvOtchetMaster = view.findViewById<CardView>(R.id.crdvOtchetMaster)
        val txtOTK = view.findViewById<TextView>(R.id.txtOTK)


        fun bind(item: CItemOperationMaster) {

            txtOTK.visibility = View.GONE
            if(item.isOTKCheck) {
                txtOTK.visibility = View.VISIBLE
            }

            txtNumberDoc.text = item.number.toInt().toString()
            txtDateDoc.text = "от "+item.date
            txtDescriptionDoc.text = item.description
            txtDepartmantDoc.text = item.department


            crdvOtchetMaster.setCardBackgroundColor(Color.WHITE)

            if(item.isAddTask) {
                crdvOtchetMaster.setCardBackgroundColor(Color.LTGRAY)
            }

            itemView.setOnClickListener {
                rowClickListiner.OnClick(adapterPosition)
            }

        }

    }


    internal fun setItemsOperationMaster(items: MutableList<CItemOperationMaster>) {
        this.t_items = items
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_operation_master,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = t_items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return t_items.size
    }

}