package com.vivcom.rhtest.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vivcom.domain.Employed
import com.vivcom.rhtest.R
import com.vivcom.rhtest.ui.common.basicDiffUtil
import com.vivcom.rhtest.ui.common.inflate
import com.vivcom.rhtest.ui.common.loadUrl
import kotlinx.android.synthetic.main.view_employed.view.*

class EmployedAdapter(private val listener: (Employed) -> Unit) :
    RecyclerView.Adapter<EmployedAdapter.ViewHolder>() {

    var employees: List<Employed> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_employed, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = employees.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employed = employees[position]
        holder.bind(employed)
        holder.itemView.setOnClickListener { listener(employed) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(employed: Employed) {
            itemView.txvDescription.text = employed.toString()
            itemView.imgAvatar.loadUrl("http://placeimg.com/640/480/tech/${employed.id}")
        }
    }
}