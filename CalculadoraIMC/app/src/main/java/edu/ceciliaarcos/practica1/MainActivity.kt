package edu.ceciliaarcos.practica1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.text.isDigitsOnly
import edu.ceciliaarcos.practica1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalcular.setOnClickListener { Calcular() }
    }

    fun Calcular() {
        val rbM: RadioButton = findViewById(R.id.rb_mujer)
        val rbH: RadioButton = findViewById(R.id.rb_hombre)
        val etPeso: EditText = findViewById(R.id.ed_peso)
        val etAltura: EditText = findViewById(R.id.ed_altura)
        val tvPeso :TextView = findViewById(R.id.tv_peso)
        val tvAltura: TextView = findViewById(R.id.tv_altura)

        // si se da alguno de estos casos entonces entra a valorar cual es error
        if (etAltura.text.isEmpty() || etPeso.text.isEmpty()
            || etPeso.text.startsWith(".")
            || etAltura.text.startsWith(".")
        ) {
            //---------------------------------------------------------------
            // ---------------POSIBLES ERRORES CONTROLADOS-------------------

            if (etPeso.text.isEmpty() && etAltura.text.isEmpty()) { //Error de todo vacio
                Toast.makeText(
                    this, R.string.msgAll, Toast.LENGTH_LONG
                ).show()
            }else{
                if (etAltura.text.isEmpty()) {                      //Error solo altura vacio
                    Toast.makeText(
                        this, R.string.msgNeedHeight, Toast.LENGTH_LONG
                    ).show()
                }
                if (etPeso.text.isEmpty()) {                        //Error solo peso vacio
                    Toast.makeText(
                        this, R.string.msgNeedWeight, Toast.LENGTH_LONG
                    ).show()
                }

            }
            if (etAltura.text.startsWith(".") || etPeso.text.startsWith(".")) { //Error para que no pueda poner "." al principio
                Toast.makeText(
                    this, R.string.msgNoPoint, Toast.LENGTH_SHORT
                ).show()
            }
            // ----------------FIN POSIBLES ERRORES-------------------
            //--------------------------------------------------------
        } else {
            //si no hay errores entonces ->
            if (rbH.isChecked) {                                    //Si el RadioButton Hombre está seleccionado
                val altura = etAltura.text.toString().toDouble()    //Convierto las entradas de texto a double
                val peso = etPeso.text.toString().toDouble()

                if (altura == 0.0 || peso == 0.0) {                 //Controlo el error de que no me introduzcan 0 ya que devuelve infinity/ NaN
                    Toast.makeText(
                        this, R.string.msgNoZero, Toast.LENGTH_SHORT //Muestro mensaje de que no me introduzca 0
                    ).show()
                } else {                                            // Si no me ha puesto un 0 entonces ->

                    val alturaDos = ((altura * altura) * 0.0001)    // conversión para pasar la altura a cm y elevarla al cuadrado
                    var calculo = peso / alturaDos                  // Fórmula
                    val txtV: TextView = findViewById(R.id.tv_calculoPeso) // Dónde se va a mostrar el resultado de la fórmula
                    var resultado= String.format("%.2f", calculo)   //Formateo el resultado para solo mostrar dos decimales
                    txtV.setText(resultado.replace('.',','))    //Me aseguro de que muestre siempre "," y no "."

                    // --------------RANGOS DE PESO------------------
                    if (calculo < 18.5) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgBajo)
                    }
                    if (calculo >= 18.5 && calculo <= 24.9) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgBien)
                    }
                    if (calculo >= 25.0 && calculo <= 29.9) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgSobrepeso)
                    }
                    if (calculo > 30) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgObeso)
                    }
                }
            }

            if (rbM.isChecked) {                                    //Si el radioButton Mujer está seleccionado...............
                val altura = etAltura.text.toString().toDouble()
                val peso = etPeso.text.toString().toDouble()

                if (altura == 0.0 || peso == 0.0) {
                    Toast.makeText(
                        this, R.string.msgNoZero, Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val alturaDos = ((altura * altura) * 0.0001)
                    var calculo = peso / alturaDos
                    val txtV: TextView = findViewById(R.id.tv_calculoPeso)
                    var resultado= String.format("%.2f", calculo)
                    txtV.setText(resultado.replace('.',','))

                    if (calculo < 18.5) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgBajo)
                    }
                    if (calculo >= 18.5 && calculo <= 23.9) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgBien)
                    }
                    if (calculo >= 24.0 && calculo <= 28.9) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgSobrepeso)
                    }
                    if (calculo > 29) {
                        val txtVmini: TextView = findViewById(R.id.tv_tipoPeso)
                        txtVmini.setText(R.string.msgObeso)
                    }
                }
            }
        }
    }
}