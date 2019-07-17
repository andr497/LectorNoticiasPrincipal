package com.example.lectornoticias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_agregar_noticia.*

class AgregarNoticia : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_noticia)

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.getReference("noticias")

        val OI:Intent=intent
        var autor = OI.getStringExtra("autor")
        tv_autor.text = autor.toString()

        bt_agregar.setOnClickListener {
            if (!TextUtils.isEmpty(ed_titulo.text) && !TextUtils.isEmpty(ed_descripcion.text)) {
                val noti = Noticias(
                    ed_titulo.text.toString(),
                    ed_descripcion.text.toString(),
                    tv_autor.text.toString()
                )
                dbReference.push().setValue(noti)
                finish()
            }
            else{
                ed_titulo.error = if(ed_titulo.text.isEmpty()){"Campo obligatorio."}else{null}
                ed_descripcion.error = if(ed_descripcion.text.isEmpty()){"Campo obligatorio"}else{null}
                Toast.makeText(this,"Rellene los campos.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}
