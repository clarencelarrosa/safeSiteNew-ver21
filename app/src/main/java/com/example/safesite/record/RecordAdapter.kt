package com.example.safesite.record

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safesite.R
import com.google.android.material.textview.MaterialTextView
import kotlinx.parcelize.Parcelize

class RecordAdapter(private var records: List<Record>) :
    RecyclerView.Adapter<RecordAdapter.RecordHolder>() {

    class RecordHolder(view:View):RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.findViewById(R.id.tvName)
        val siteName: MaterialTextView = view.findViewById(R.id.tvSiteName)
        val ppe: MaterialTextView = view.findViewById(R.id.tvPPE)
        val date: MaterialTextView = view.findViewById(R.id.tvDate)
        val total: MaterialTextView = view.findViewById(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return RecordHolder(view)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {

        val record=records[position]
        val context=holder.date.context

        holder.name.text=record.name
        holder.siteName.text=record.siteName
        holder.ppe.text=record.ppe
        holder.date.text=record.date
        holder.total.text=record.total.toString()

        holder.itemView.setOnClickListener{
            val intent= Intent(context, UpdateRecordActivity::class.java)
            intent.putExtra("EMPLOYEE_DATA",record)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun setListData(record: List<Record>) {
        this.records = record
        notifyDataSetChanged()
    }
}