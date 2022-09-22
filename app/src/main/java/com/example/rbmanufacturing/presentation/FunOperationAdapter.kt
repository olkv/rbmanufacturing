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

class FunOperationAdapter(operationArray:MutableList<CFunOperation>, context: Context): RecyclerView.Adapter<FunOperationAdapter.ViewHolder>() {

    var t_context = context
    var t_operationArray = operationArray

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val txtNameOperation = view.findViewById<TextView>(R.id.txtNameOperation)
        val txtCodeOperation = view.findViewById<TextView>(R.id.txtCodeOperation)
        val txtDescriptionOperation = view.findViewById<TextView>(R.id.txtDescriptionOperation)

        fun bind(operationItem:CFunOperation, context: Context) {
            txtNameOperation.text = operationItem.name
            txtCodeOperation.text = operationItem.code
            txtDescriptionOperation.text = operationItem.description

            itemView.setOnClickListener {
                Log.d("RBM",operationItem.code)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(t_context)
        return ViewHolder(inflater.inflate(R.layout.item_fun_operation,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var itemOperation = t_operationArray[position]
        holder.bind(itemOperation, t_context)    }

    override fun getItemCount(): Int {
        return t_operationArray.size
    }

}