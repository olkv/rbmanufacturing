package com.example.rbmanufacturing.presentation.moveitemmanf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CItemWarehouse

class MoveItemManfAdapter(context: Context): RecyclerView.Adapter<MoveItemManfAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_items = mutableListOf<CItemWarehouse>()//operationArray

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtNameItem = view.findViewById<TextView>(R.id.txtNameItem)
        val txtParamenerItem = view.findViewById<TextView>(R.id.txtParamenerItem)
        val txtStageItem = view.findViewById<TextView>(R.id.txtStageItem)
        val txtCustomItem = view.findViewById<TextView>(R.id.txtCustomItem)
        val txtAppointmentItem = view.findViewById<TextView>(R.id.txtAppointmentItem)
        val txtCountItem = view.findViewById<TextView>(R.id.txtCountItem)
        val editCountItem = view.findViewById<EditText>(R.id.editCountItem)
        val switchFullCount = view.findViewById<Switch>(R.id.switchFullCount)


        fun bind(item: CItemWarehouse) {
            txtNameItem.text = item.name
            txtParamenerItem.text = item.parameter
            txtStageItem.text = item.stage
            txtCustomItem.text = item.custom
            txtAppointmentItem.text = item.appointment
            txtCountItem.text = item.count.toString()
            editCountItem.setText(item.editcount.toString())

            itemView.setOnClickListener {
                //Log.d("MYLOG",operationItem.code)
                //itemClickListener.OnClick(operationItem.code)
            }

            switchFullCount.setOnClickListener {
                if (switchFullCount.isChecked) {
                    editCountItem.setText(item.count.toString())
                }
                else {
                    editCountItem.setText("0.0")
                }
            }

        }
    }

    internal fun setItemManf(items: MutableList<CItemWarehouse>) {
        this.t_items = items
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_warehouse_manf,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = t_items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return t_items.size
    }
}