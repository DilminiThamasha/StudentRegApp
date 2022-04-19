package com.crude.crudestudentregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var e_name: EditText
    private lateinit var e_address: EditText
    private lateinit var btn_add:Button
    private lateinit var btn_view:Button
    private lateinit var btn_update:Button

    private lateinit var sqlHelper: SQLhelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter?=null
    private var std:StudentModel? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecycleView()
        sqlHelper = SQLhelper(this)

        btn_add.setOnClickListener { addStudent() }
        btn_view.setOnClickListener { getStudent() }
        btn_update.setOnClickListener { updateStudent() }
        adapter?.setOnClickItem { Toast.makeText(this,it.name,Toast.LENGTH_SHORT).show()

        e_name.setText(it.name)
            e_address.setText(it.address)
            std = it
        }

        adapter?.onClickDeleteItem {
             deleteStudent(it.id)
        }
    }

    private fun getStudent(){
        val stdList = sqlHelper.getAllStudent()
        Log.e("pppp","${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun addStudent(){
        val name = e_name.text.toString()
        val address = e_address.text.toString()

        if(name.isEmpty() || address.isEmpty()){
            Toast.makeText(this,"Please Enter Required Filed", Toast.LENGTH_SHORT).show()
        }
        else{
            val std = StudentModel(name = name, address = address)
            val status = sqlHelper.insertStudent(std)

            if (status > -1){
                Toast.makeText(this, "Added Successfully!",Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            }
            else{
                Toast.makeText(this,"Record Not Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStudent(){
        val name = e_name.text.toString()
        val address = e_address.text.toString()

        if (name == std?.name && address == std?.address){
             Toast.makeText(this, "Recorde not changed",Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, address = address)
        val status = sqlHelper.updateStudent(std)
        if (status > -1){
            clearEditText()
            getStudent()
        }
        else{
            Toast.makeText(this,"Update Failed", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteStudent(id:Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure ypu want delete this item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){
            dialog, _ ->
            sqlHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){
                dialog, _ ->  dialog.dismiss()
        }
    }

    private fun clearEditText(){
        e_name.setText("")
        e_address.setText("")
        e_name.requestFocus()
    }

    private fun initRecycleView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }
    private fun initView(){
        e_name = findViewById(R.id.S_name)
        e_address = findViewById(R.id.S_address)
        btn_add = findViewById(R.id.add)
        btn_view = findViewById(R.id.view)
        btn_update = findViewById(R.id.update)
        recyclerView=findViewById(R.id.recycleView)

    }
}