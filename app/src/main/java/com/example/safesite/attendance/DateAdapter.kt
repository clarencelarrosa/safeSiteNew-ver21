package com.example.safesite.attendance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.safesite.R
import com.example.safesite.databinding.DateItemBinding


class DateAdapter(
    var context: Context,
    var dateListArray: ArrayList<Any>
) : BaseAdapter() {

    override fun getCount(): Int {
        return dateListArray.size
    }

    override fun getItem(a0: Int): Date {
        return dateListArray[a0] as Date
    }

    override fun getItemId(a0: Int): Long {
        return a0.toLong()
    }

    override fun getView(a0: Int, a1: View?, pa2: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
        val binding = DateItemBinding.inflate(layoutInflater)
        val dateItemNameView = binding.dateItemName
        val dateItemSizeView = binding.dateItemSize
        var size = 0
        for (key in getItem(a0).products.keys) {
            size += getItem(a0).products[key]!!.size
        }
        dateItemNameView.text = getItem(a0).name
        if (size == 0) {
            dateItemSizeView.text = context.getString(R.string.product_quantity_0)
        } else {
            dateItemSizeView.text = String.format(
                context.resources.getQuantityString(
                    R.plurals.products_quantity,
                    size
                ), size
            )
        }
        return binding.root
    }
}