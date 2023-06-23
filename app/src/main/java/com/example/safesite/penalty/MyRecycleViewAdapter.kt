package com.example.safesite.penalty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.safesite.R
import com.example.safesite.databinding.ListItemBinding
import com.example.safesite.penalty.database.Penalty


class MyRecyclerViewAdapter(private val clickListener: (Penalty) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val penaltyList = ArrayList<Penalty>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return penaltyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(penaltyList[position], clickListener)
    }

    fun setList(penalty: List<Penalty>) {
        penaltyList.clear()
        penaltyList.addAll(penalty)

    }

}
class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(penalty: Penalty, clickListener: (Penalty) -> Unit) {
        binding.txtPenaltyDate.text= "Date: " + penalty.penaltyDate
        binding.txtPenaltyAmount.text = "Total amount of penalty: " +penalty.penaltyAmount
        binding.txtPenaltyDesc.text = "Penalty Description: " +penalty.penaltyDesc

        binding.listItemLayout.setOnClickListener {
            clickListener(penalty)
        }
    }
}