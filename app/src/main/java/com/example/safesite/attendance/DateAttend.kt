package com.example.safesite.attendance

class DateAttend(var name: String, val products: HashMap<String, ArrayList<Attendance>>) {

    override fun toString(): String {
        return "\nDateItem(name='$name', " +
                "products=$products)"
    }
}