package com.crude.crudestudentregistration

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.lang.UProperty.NAME
import android.os.Build.ID
import android.provider.ContactsContract.CommonDataKinds.Email.ADDRESS

class SQLhelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "student.db"
        private const val TBL_STUDENT = "tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val ADDRESS = "address"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStudent =
            ("CREATE_TABLE$TBL_STUDENT($ID INTEGER PRIMARY KEY,$NAME TEXT,$ADDRESS TEXT )")
        db?.execSQL(createTableStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${com.crude.crudestudentregistration.SQLhelper.Companion.TBL_STUDENT}")
        onCreate(db)
    }

    fun insertStudent(std: StudentModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(ADDRESS, std.address)

        val success = db.insert(TBL_STUDENT, null, contentValues)
        db.close()
        return success

    }

    @SuppressLint("Range")
    fun getAllStudent(): ArrayList<StudentModel> {
        val stdList: ArrayList<StudentModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var address: String

        if (cursor.moveToNext()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                address = cursor.getString(cursor.getColumnIndex("address"))

                val std = StudentModel(id = id, name = name, address = address)
                stdList.add(std)
            } while (cursor.moveToNext())
        }

        return stdList
    }

    fun updateStudent(std: StudentModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(NAME,std.name)
        contentValues.put(ADDRESS,std.address)

        val success = db.update(TBL_STUDENT,contentValues,"id" + std.id, null)
        db.close()
        return success

    }

    fun deleteStudentById(id:Int):Int{
         val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_STUDENT,"id=$id",null)
        db.close()
        return success

    }
}
