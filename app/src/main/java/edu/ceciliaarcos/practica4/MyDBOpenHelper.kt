package edu.ceciliaarcos.practica4

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import edu.ceciliaarcos.practica4.data.MyIMC

class MyDBOpenHelper(context: MainActivity, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION){

    companion object{
        val DATABASE_VERSION=1;
        val DATABASE_NAME="imc.db"
        val TABLA_IMCS ="IMCs"
        val COLUMNA_ID="_id"
        val COLUMNA_SEXO="sexo"
        val COLUMNA_ESTADO="estado"
        val COLUMNA_PESO="peso"
        val COLUMNA_FECHA="fecha"
        val COLUMNA_ALTURA="altura"
        val COLUMNA_IMC ="imc"

    }

    //Método para crear las tablas al crear la base de datos

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val crearTablaIMCs= "CREATE TABLE $TABLA_IMCS"+
                    "($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "$COLUMNA_SEXO TEXT, "+
                    "$COLUMNA_ALTURA TEXT, "+
                    "$COLUMNA_ESTADO TEXT, "+
                    "$COLUMNA_FECHA TEXT, "+
                    "$COLUMNA_IMC TEXT, "+
                    "$COLUMNA_PESO TEXT)"
            db!!.execSQL(crearTablaIMCs)

        }catch (e:SQLiteException){

        }
    }


    //Método para actualizar la BD
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {

           val droTablaIMCs ="DROP TABLE IF EXISTS $TABLA_IMCS"
            db!!.execSQL(droTablaIMCs)
            onCreate(db)

        }catch (e:SQLiteException){}
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    //Añadir los datos recogidos del ImcFragment
    fun addIMC(datos:MyIMC){
        val data = ContentValues()

        data.put(COLUMNA_ALTURA, datos.altura.toString())
        data.put(COLUMNA_IMC, datos.imc.toString())
        data.put(COLUMNA_PESO, datos.peso.toString())
        data.put(COLUMNA_ESTADO, datos.estado.toString())
        data.put(COLUMNA_FECHA, datos.fecha.toString())
        data.put(COLUMNA_SEXO, datos.sexo.toString())

        //Abrir BD en modo escritura
        val db = this.writableDatabase
        db.insert(TABLA_IMCS,null,data)
        db.close()

    }

    //funcion para eliminar un imc a través de su ID
    fun deleteIMC(id : Int):Int{
        val args = arrayOf(id.toString())

        //abrir bd en modo escritura
        val db = this.writableDatabase

        val result = db.delete(TABLA_IMCS, "$COLUMNA_ID = ?", args)


        db.close()

        return result


    }

}