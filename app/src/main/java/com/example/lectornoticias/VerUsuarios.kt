package com.example.lectornoticias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_ver_usuarios.*

class VerUsuarios : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuarios)

        val bd = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        bd.firestoreSettings=settings


        bd.collection("Usuarios").
            addSnapshotListener(EventListener<QuerySnapshot>{ snapshots, e ->

                if (e != null) {
                    Log.w("Realtime", "listen:error", e)
                    return@EventListener
                }

                listausuarios= snapshots!!.documents
                //Toast.makeText(this,listaproducto.size.toString(),Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext,"Numero de usuarios: " + listausuarios.size.toString(),Toast.LENGTH_SHORT).show()

                rvusuarios.layoutManager=LinearLayoutManager(applicationContext,LinearLayout.VERTICAL,false)
                var adapter=Adapterusuario(listausuarios)
                rvusuarios.adapter=adapter

            })


    }

    override fun onRestart() {
        super.onRestart()
    }
}

