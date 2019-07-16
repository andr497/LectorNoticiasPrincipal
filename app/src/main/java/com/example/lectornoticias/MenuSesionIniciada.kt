package com.example.lectornoticias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_menu_sesion_iniciada.*

class MenuSesionIniciada : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_sesion_iniciada)
        setSupportActionBar(tb_sesion)
        setTitle("Bienvenido")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sesion,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id=item?.itemId

        if(id==R.id.item_cerrar_sesion){
            Toast.makeText(this, "Desconectar", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
