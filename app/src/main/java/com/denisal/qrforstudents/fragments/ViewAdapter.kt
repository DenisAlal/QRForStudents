package com.denisal.qrforstudents.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.denisal.qrforstudents.R

class ViewAdapter(private val list: List<GradeModel>):
    RecyclerView.Adapter<ViewAdapter.RowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_list_item, parent, false)
        return RowViewHolder(itemView)
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0) {
            holder.apply {
                setHeaderBg(courseName)
                setHeaderBg(lessonName)
                setHeaderBg(taskName)
                setHeaderBg(date)
                setHeaderBg(valueGrade)
                courseName.text = "Предмет"
                lessonName.text = "Занятие"
                taskName.text = "Задание"
                date.text = "Дата выставления"
                valueGrade.text = "Оценка"


            }
        } else {
            val modal = list[rowPos - 1]

            holder.apply {
                setContentBg(courseName)
                setContentBg(lessonName)
                setContentBg(taskName)
                setContentBg(date)
                setContentBg(valueGrade)
                courseName.text = modal.courseName
                lessonName.text = modal.lessonName
                taskName.text = modal.taskName
                date.text = modal.date
                valueGrade.text = modal.valueGrade
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1 // one more to add header row
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseName: TextView = itemView.findViewById(R.id.courseName)
        val lessonName: TextView = itemView.findViewById(R.id.lessonName)
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val date: TextView = itemView.findViewById(R.id.date)
        val valueGrade: TextView = itemView.findViewById(R.id.valueGrade)

    }
}