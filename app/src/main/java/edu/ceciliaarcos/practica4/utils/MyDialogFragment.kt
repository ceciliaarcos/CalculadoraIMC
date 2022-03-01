package edu.ceciliaarcos.practica4.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import edu.ceciliaarcos.practica4.MainActivity
import edu.ceciliaarcos.practica4.R
import edu.ceciliaarcos.practica4.data.MyIMC

class MyDialogFragment(data: MyIMC) : DialogFragment() {

    private var persona: MyIMC = data

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(R.string.msgTitulo)
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, which ->
                    // si se pulsa ok se guardan los datos en el IMC
                    MainActivity.imcsBD.addIMC(persona)
                    //muestro snackbar avisando de que se ha guardado con éxito en la bd el IMC
                    Snackbar.make(
                        it.findViewById(android.R.id.content),
                        R.string.msgAceptado,
                        Snackbar.LENGTH_LONG
                    ).show()

                }
                .setNegativeButton(
                    android.R.string.cancel
                ) { dialog, which ->
                    //si se pulsa cancelar se avisa de que no se han guardado los datos en la bd
                    Snackbar.make(
                        it.findViewById(android.R.id.content),
                        R.string.msgRechazado,
                        Snackbar.LENGTH_LONG
                    ).show()

                }
            builder.create()
        } ?: throw IllegalStateException("La Activity no puede ser nula!!!!!!")
    }
}