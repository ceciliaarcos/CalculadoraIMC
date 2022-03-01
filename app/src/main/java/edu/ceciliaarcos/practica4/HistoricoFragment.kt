package edu.ceciliaarcos.practica4

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ceciliaarcos.practica4.databinding.FragmentHistoricoBinding

class HistoricoFragment : Fragment() {

    private lateinit var binding: FragmentHistoricoBinding
    private val myAdapter: MyRecyclerAdapter = MyRecyclerAdapter()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoricoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cargarDatos()
    }


    override fun onStart() {
        super.onStart()

        cargarDatos()
    }

    override fun onPause() {
        super.onPause()
        cargarDatos()
    }

    override fun onResume() {
        super.onResume()

        cargarDatos()
    }


    private fun cargarDatos() {

        //se llama a la BD en modo escritura y creo el select
        val db:SQLiteDatabase = MainActivity.imcsBD.readableDatabase
        val cursor : Cursor = db.rawQuery(
            "SELECT * FROM ${MyDBOpenHelper.TABLA_IMCS};", null
        )

        //Averiguar si existe algun imc guardado
        if(cursor.moveToFirst()) { //si los hay muestro el RV
            binding.tvInfo.isVisible = false
            myAdapter.MyRecyclerAdapter(requireContext(), cursor)
            binding.rvIMCs.setHasFixedSize(true)
            binding.rvIMCs.layoutManager = LinearLayoutManager(context)
            binding.rvIMCs.adapter = myAdapter
        }else{ //-> sino, muestro que no hay datos todavía
            binding.tvInfo.isVisible = true
        }
        db.close()

    }

}