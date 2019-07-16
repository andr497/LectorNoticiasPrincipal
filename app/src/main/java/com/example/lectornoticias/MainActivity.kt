package com.example.lectornoticias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
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
        private lateinit var auth: FirebaseAuth
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id=item?.itemId

        if(id==R.id.item_iniciar){
            //Toast.makeText(this,"Practica Toolbar",Toast.LENGTH_SHORT).show()

            val dialog = AlertDialog.Builder(this@MainActivity)
            val view = layoutInflater.inflate(R.layout.dialog, null)

            val etcorreo = view.findViewById<EditText>(R.id.edtCorreo)
            val etpass = view.findViewById<EditText>(R.id.edtPass)
            val btlog = view.findViewById<Button>(R.id.btnLogin)
            val txtrestar=view.findViewById<TextView>(R.id.txt_restaurar)
            val pb_login=view.findViewById<ProgressBar>(R.id.pb_barra)

            dialog.setView(view)
            dialog.setCancelable(true)

            auth=FirebaseAuth.getInstance()

            val dialogShow = dialog.create()
            dialogShow.show()

            btlog.setOnClickListener {
                val correo: String = etcorreo.text.toString()
                val pass: String = etpass.text.toString()

                if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(pass)) {

                    auth.signInWithEmailAndPassword(correo, pass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                pb_login.visibility= View.VISIBLE
                                Toast.makeText(this, "Usted se ha logeado", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,MenuSesionIniciada::class.java))
                            } else {
                                Toast.makeText(this, "Usuario o clave incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        }

                } else {
                    Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show()
                }
            }
            txtrestar.setOnClickListener {
                startActivity(Intent(this,ForgotPass::class.java))
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
