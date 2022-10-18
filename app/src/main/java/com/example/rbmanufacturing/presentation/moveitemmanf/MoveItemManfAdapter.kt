package com.example.rbmanufacturing.presentation.moveitemmanf

import android.content.Context
import android.graphics.Color
import android.graphics.Color.WHITE
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.repository.RowClickListiner

class MoveItemManfAdapter(context: Context, val rowClickListiner: RowClickListiner): RecyclerView.Adapter<MoveItemManfAdapter.ViewHolder>() {

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
        val btnAllCount = view.findViewById<ToggleButton>(R.id.btnAllCount)
        val rowItem = view.findViewById<LinearLayout>(R.id.rowItem)


        fun bind(item: CItemWarehouse) {
            txtNameItem.text = item.name
            txtParamenerItem.text = item.parameter
            txtStageItem.text = item.stage
            txtCustomItem.text = item.custom
            txtAppointmentItem.text = item.appointment
            txtCountItem.text = item.count.toString()
            editCountItem.setText(item.editcount.toString())

            btnAllCount.isChecked = false
            rowItem.setBackgroundColor(Color.WHITE)

            if(item.editcount>0) {
                rowItem.setBackgroundColor(Color.LTGRAY)
            }

            if (item.count==item.editcount && item.editcount>0) {
                btnAllCount.isChecked = true
            }
1
            itemView.setOnClickListener {
                //Log.d("MYLOG",operationItem.code)
                //itemClickListener.OnClick(operationItem.code)
            }

            //Переключатель если включен, тогда все количество, если нет тогда 0
            btnAllCount.setOnClickListener {

                val isChecked = btnAllCount.isChecked
                if (isChecked) {
                    editCountItem.setText(item.count.toString())
                    rowItem.setBackgroundColor(Color.LTGRAY)
                }
                else {
                    editCountItem.setText("0.0")
                    rowItem.setBackgroundColor(Color.WHITE)
                }

                t_items[adapterPosition].editcount = editCountItem.text.toString().toDouble()

            }

            //Добавление слушателя изменения поля
            editCountItem.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    if(s!!.isNotEmpty()) {
                        if (s.isDigitsOnly()) {
                            t_items[adapterPosition].editcount = s.toString().toDouble()
                            btnAllCount.isChecked = t_items[adapterPosition].editcount==t_items[adapterPosition].count && t_items[adapterPosition].editcount>0

                            if( t_items[adapterPosition].editcount>0) {
                                rowItem.setBackgroundColor(Color.LTGRAY)
                            }
                            else {
                                rowItem.setBackgroundColor(Color.WHITE)
                            }

                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            itemView.setOnClickListener {
                rowClickListiner.OnClick(adapterPosition)
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