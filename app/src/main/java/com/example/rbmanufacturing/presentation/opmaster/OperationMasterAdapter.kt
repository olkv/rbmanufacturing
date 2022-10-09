package com.example.rbmanufacturing.presentation.opmaster

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rbmanufacturing.R
import com.example.rbmanufacturing.domain.models.CItemOperationMaster
import com.example.rbmanufacturing.domain.models.CItemWarehouse
import com.example.rbmanufacturing.domain.repository.RowClickListiner

class OperationMasterAdapter(context: Context, val rowClickListiner: RowClickListiner): RecyclerView.Adapter<OperationMasterAdapter.ViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var t_items =  mutableListOf<CItemOperationMaster>()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(item: CItemOperationMaster) {



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