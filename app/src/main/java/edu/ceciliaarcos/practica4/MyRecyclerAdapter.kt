package edu.ceciliaarcos.practica4

import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.ceciliaarcos.practica4.databinding.ItemImcBinding
import edu.ceciliaarcos.practica4.utils.MyFunctions


class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var cursor: Cursor


    fun MyRecyclerAdapter(context: Context, imcs: Cursor) {
        this.context = context
        this.cursor = imcs

    }


    //completar los datos de cada vista con el ViewHolder
    override fun onBindViewHolder(holder: MyRecyclerAdapter.ViewHolder, position: Int) {
        //recorro el cursor
        cursor.moveToPosition(position)
        holder.bind()
    }

    // Devuelve el ViewHolder ya configurado.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemImcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root,
        )
    }


    override fun getItemCount(): Int {
        return cursor.count
    }

    //función para crear un alertdialogo en el cual se muestra si se desea borrar o no dicho IMC
    fun borrarID(context: Context, view: View, position: Int) {

        val builder = AlertDialog.Builder(context)

        val binding = ItemImcBinding.bind(view)

        //Creo el AlertDialog
        builder.apply {
            setTitle(R.string.msgTituloBorrado)//titulo
            setMessage(R.string.msgBorrado)//mensaje

            setPositiveButton(
                android.R.string.ok //si se pulsa el botón aceptar,
            )
            { dialog, which ->

                MainActivity.imcsBD.deleteIMC(Integer.parseInt(binding.tvIDbd.text as String?)) //paso a la BD el ID del IMC a borrar
                                                                                                //recogido desde el txtView que tengo invisible

                //Muestro snackbar avisando de que se han borrado los datos.
                Snackbar.make(
                    view,
                    R.string.msgBorradoAceptado,
                    Snackbar.LENGTH_LONG
                ).show()

                return@setPositiveButton

            }
            setNegativeButton(
                android.R.string.cancel //si se pulsa el botón cancelar
            ) { dialog, which ->
                //muestro snackbar avisando que no se han borrado los datos

                Snackbar.make(
                    view,
                    R.string.msgBorradoAnulado,
                    Snackbar.LENGTH_LONG
                ).show()
                return@setNegativeButton
            }

        }.show() //mostrar el alertdialog

    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemImcBinding.bind(view)

        fun bind() {

            binding.tvSexo.text = cursor.getString(1)
            binding.tvAlturaItemList.text = context.getString(
                R.string.labelAlturaItem,
                String.format("%.1f", cursor.getString(2)!!.toDouble())
            )
            binding.tvEstado.text = cursor.getString(3)
            //FEcha

            binding.tvDia.text = cursor.getString(4).split("-")[0]
            binding.tvMes.text =
                context.resources.getStringArray(R.array.meses)[cursor.getString(4)
                    .split("-")[1].toInt() - 1]
            binding.tvAnyo.text = cursor.getString(4).split("-")[2]


            binding.tvIMChistorico.text = String.format("%.2f", cursor.getString(5)!!.toDouble())

            binding.tvPesoItemList.text = context.getString(
                R.string.labelPesoItem,
                String.format("%.1f", cursor.getString(6)!!.toDouble())
            )
            binding.tvIDbd.text = cursor.getString(0)


            itemView.setOnClickListener {
                MyFunctions().showSimpleSnackBar(
                    binding.root,
                    "${binding.tvEstado.text} (${binding.tvIMChistorico.text})"
                )
            }


            //si se hace pulsación larga entonces muestra el dialog de borrarID y le pasas dicho ID(recogido del textView)
            itemView.setOnLongClickListener {

                borrarID(context, it, Integer.parseInt(binding.tvIDbd.text as String?))

                return@setOnLongClickListener false

            }


        }
    }


}

