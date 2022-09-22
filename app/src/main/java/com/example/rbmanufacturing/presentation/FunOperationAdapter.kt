package com.example.rbmanufacturing.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CFunOperation

class FunOperationAdapter(context: Context): RecyclerView.Adapter<FunOperationAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_operationArray = mutableListOf<CFunOperation>()//operationArray

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val txtNameOperation = view.findViewById<TextView>(R.id.txtNameOperation)
        val txtCodeOperation = view.findViewById<TextView>(R.id.txtCodeOperation)
        val txtDescriptionOperation = view.findViewById<TextView>(R.id.txtDescriptionOperation)

        fun bind(operationItem:CFunOperation) {
            txtNameOperation.text = operationItem.name
            txtCodeOperation.text = operationItem.code
            txtDescriptionOperation.text = operationItem.description

            itemView.setOnClickListener {
                Log.d("MYLOG",operationItem.code)
            }

        }
    }

    internal fun setOperationArray(operationArray: MutableList<CFunOperation>) {
        this.t_operationArray = operationArray
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_fun_operation,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itemOperation = t_operationArray[position]
        holder.bind(itemOperation)
    }

    override fun getItemCount(): Int {
        return t_operationArray.size
    }

}