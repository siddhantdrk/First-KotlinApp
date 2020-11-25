package com.example.pacabanades

import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.jar.Attributes


class Activity2 : AppCompatActivity() {

    lateinit var helper:MyDbHelper
    lateinit var db:SQLiteDatabase
    private lateinit var cursor:Cursor
    var cv=ContentValues()
    var check:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        Toast.makeText(applicationContext, "esto es Activity2",Toast.LENGTH_SHORT).show()

        val createTableBtn=findViewById<Button>(R.id.create_table_btn);
        val insertDataBtn=findViewById<Button>(R.id.insert_data_btn);
        val consultDataBtn=findViewById<Button>(R.id.Consult_data_btn);
        val gotBackToActivity1=findViewById<Button>(R.id.go_back_to_activity_1_btn);



        createTableBtn.setOnClickListener{
            helper=MyDbHelper(applicationContext)
            check=false
            db=helper.readableDatabase
            cursor = db.rawQuery("SELECT * FROM USERS",null)
            Toast.makeText(applicationContext, "Crear tabla.",Toast.LENGTH_SHORT).show()
        }

        //Inflate the dialog with custom view

        insertDataBtn.setOnClickListener{

            if(check){
                Toast.makeText(applicationContext, "Primero cree la tabla",Toast.LENGTH_SHORT).show()
            }
            else {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.insert_data, null)
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Insertar datos")

                builder.setView(mDialogView)

                builder.setPositiveButton("Insertar",
                        DialogInterface.OnClickListener { dialog, which ->
                            insertData(mDialogView.findViewById<EditText>(R.id.name).text.toString(),
                                    mDialogView.findViewById<EditText>(R.id.surname).text.toString(), mDialogView.findViewById<EditText>(R.id.age).text.toString())
                        })
                builder.setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
            }

        }

        consultDataBtn.setOnClickListener{
            helper=MyDbHelper(applicationContext)
            db=helper.readableDatabase
            cursor = db.rawQuery("SELECT * FROM USERS", null)
            if(check){
                Toast.makeText(applicationContext, "Primero cree la tabla",Toast.LENGTH_SHORT).show()
            }
            else if(cursor.count==0){
                Toast.makeText(applicationContext, "Primero Insertar datos"+cursor.count,Toast.LENGTH_SHORT).show()
            }
            else {
                cursor.moveToLast()
//                Toast.makeText(applicationContext, "Nombre : "+cursor.getString(1)+"\nApellido : "
//                        +cursor.getString(2)+"\nAños : "+cursor.getString(3), Toast.LENGTH_LONG).show()

                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Consultar datos")

                builder.setMessage("Nombre : "+cursor.getString(1)+"\nApellido : "
                        +cursor.getString(2)+"\nAños : "+cursor.getString(3))

                builder.setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()

            }
        }

        gotBackToActivity1.setOnClickListener { // Handler code here.
            Toast.makeText(applicationContext, "de regreso Activity1",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun insertData(Name: String,Surname:String,Age:String) {
        cv.put("U_NAME",Name)
        cv.put("U_SURNAME",Surname)
        cv.put("U_AGE",Age)
        db.insert("USERS",null,cv)
        Toast.makeText(applicationContext, "Insertar datos",Toast.LENGTH_SHORT).show()
    }
}