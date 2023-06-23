package com.example.safesite.attendance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.safesite.R
import com.example.safesite.databinding.AttendanceCategoryBinding
import com.example.safesite.databinding.AttendanceItemBinding


class AttendanceAdapter(
    var context: Context,
    var attendanceCategory: Array<String>,
    var expandableAttendanceList: HashMap<String, ArrayList<Attendance>>
) : BaseExpandableListAdapter() {

    //aattendance
    override fun getGroupCount(): Int {
        return expandableAttendanceList.size
    }
    override fun getGroup(a0: Int): String {
        return attendanceCategory[a0]
    }
    override fun getChildrenCount(a0: Int): Int {
        return expandableAttendanceList[attendanceCategory[a0]]!!.size
    }

    override fun getChild(a0: Int, a1: Int): Attendance {
        return expandableAttendanceList[attendanceCategory[a0]]!![a1]
    }

    override fun isChildSelectable(a0: Int, a1: Int): Boolean {
        return true
    }

    override fun getGroupId(a0: Int): Long {
        return a0.toLong()
    }

    override fun getChildId(a0: Int, a1: Int): Long {
        return a0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getChildView(a0: Int, a1: Int,
                              a2: Boolean, a3: View?,
                              a4
    : ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
        val binding = AttendanceItemBinding.inflate(layoutInflater)

        val attendanceNameView = binding.attendanceName

        val attendanceCheckedView = binding.attendanceCheckBox
        attendanceCheckedView.setOnCheckedChangeListener { _, checked ->
            getChild(a0, a1).checked = checked
        }
        attendanceNameView.text = getChild(a0, a1).name
        attendanceCheckedView.isChecked = getChild(a0, a1).checked
        return binding.root
    }

    override fun getGroupView(a0: Int, a1: Boolean, a2: View?, a3: ViewGroup?)
    : View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as
                    LayoutInflater
        val binding = AttendanceCategoryBinding.inflate(layoutInflater)

        val attendanceCategoryView = binding.attendanceCategory
        attendanceCategoryView.text = getGroup(a0)
        return binding.root
    }
}
