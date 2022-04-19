package com.crude.crudestudentregistration

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewMolder>(){
    private var stdList:ArrayList<StudentModel> = ArrayList()
    private var onClickItem:((StudentModel) -> Unit)? = null
    private var onClickDeleteItem:((StudentModel) -> Unit)? = null



    fun addItems(items:ArrayList<StudentModel>){
        this.stdList=items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel) -> Unit){
        this.onClickItem = callback
    }

    fun onClickDeleteItem(callback: (StudentModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewMolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item_std,parent,false)
    )

    override fun onBindViewHolder(holder: StudentViewMolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.deleteButton.setOnClickListener{onClickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewMolder(var view:View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        var deleteButton = view.findViewById<Button>(R.id.deleteBtn)


        fun bindView(std:StudentModel){
            id.text = std.id.toString()
            name.text=std.name
            address.text=std.address
        }
    }
}