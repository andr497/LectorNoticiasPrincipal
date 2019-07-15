package com.example.lectornoticias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_main.*

lateinit var listausuarios: MutableList<DocumentSnapshot>

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tb_inicio)
        setTitle("Inicio")

        button.setOnClickListener {
            val a = Intent(this, VerUsuarios::class.java)
            startActivity(a)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_inicial,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id=item?.itemId

        if(id==R.id.item_iniciar){
            //Toast.makeText(this,"Practica Toolbar",Toast.LENGTH_SHORT).show()

            val dialog = AlertDialog.Builder(this@MainActivity)
            val view = layoutInflater.inflate(R.layout.dialog, null)

            val etuser = view.findViewById<EditText>(R.id.edtUser)
            val etpass = view.findViewById<EditText>(R.id.edtPass)
            val btlog = view.findViewById<Button>(R.id.btnLogin)

            dialog.setView(view)
            dialog.setCancelable(true)

            val dialogShow = dialog.create()
            dialogShow.show()

            btlog.setOnClickListener {
                if (!etuser.text.isEmpty() && !etpass.text.isEmpty()){
                    setTitle("Hola " + etuser.text)
                    Toast.makeText(this, getText(R.string.msj_exito), Toast.LENGTH_SHORT).show()
                    dialogShow.dismiss()

                }else{
                    Toast.makeText(this, getText(R.string.msj_error), Toast.LENGTH_SHORT).show()
                }
            }
            return true
        }
        if(id==R.id.item_crear_cuenta){
            val a = Intent(this,CrearCuenta::class.java)
            startActivity(a)
        }
        if(id==R.id.item_ver_noticia)
        {
            Toast.makeText(this, "Ver noticia no disponible", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}

