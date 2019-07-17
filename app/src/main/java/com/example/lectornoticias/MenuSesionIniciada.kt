package com.example.lectornoticias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu_sesion_iniciada.*

class MenuSesionIniciada : AppCompatActivity() {
    class OnlineMatch() {}
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_sesion_iniciada)
        setSupportActionBar(tb_sesion)
        setTitle("Bienvenido")

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.getReference("usuarios")

        dbReference.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val usuarioDS = p0.getValue(Usuarios::class.java)
                if (usuarioDS != null) {
                    Toast.makeText(this@MenuSesionIniciada, "Hola " + usuarioDS.nombre, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sesion,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id=item?.itemId

        if(id==R.id.item_cerrar_sesion){
            Toast.makeText(this, "Desconectar", Toast.LENGTH_SHORT).show()
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
