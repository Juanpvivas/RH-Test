package com.vivcom.rhtest.ui.detailEmployed

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vivcom.domain.Employed
import com.vivcom.rhtest.R
import com.vivcom.rhtest.ui.common.basicDiffUtil
import com.vivcom.rhtest.ui.common.inflate
import kotlinx.android.synthetic.main.view_subordinate.view.*

class SubordinatesAdapter :
    RecyclerView.Adapter<SubordinatesAdapter.ViewHolder>() {

    var employees: List<Employed> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_subordinate, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = employees.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employed = employees[position]
        holder.bind(employed)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(employed: Employed) {
            itemView.txvNameSubordinate.text = employed.description
        }
    }
}