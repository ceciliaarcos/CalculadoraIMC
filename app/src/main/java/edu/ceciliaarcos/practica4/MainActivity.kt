package edu.ceciliaarcos.practica4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.ceciliaarcos.practica4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Uso companionObject para permitir el paso de información entre activitys.
    companion object{

        lateinit var imcsBD : MyDBOpenHelper

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se carga la toolbar.
        setSupportActionBar(binding.toolbar)

        // Se crea el adapter.
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Se añaden los fragments y los títulos de pestañas.
        adapter.addFragment(ImcFragment(), "IMC")
        adapter.addFragment(HistoricoFragment(), "Histórico")

        // Se asocia el adapter.
        binding.viewPager.adapter = adapter

        // Se cargan las tabs.
        binding.tabs.setupWithViewPager(binding.viewPager)

        //llamar  a la bd
        imcsBD = MyDBOpenHelper(this, null)
    }
}