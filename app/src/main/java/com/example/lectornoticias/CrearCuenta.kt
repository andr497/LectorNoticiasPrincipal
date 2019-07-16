package com.example.lectornoticias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Database
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_crear_cuenta.*
import kotlinx.android.synthetic.main.dialog.*
import kotlin.collections.HashMap

val usu=Usuarios()
class CrearCuenta : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        usu.nombre = ed_nombre.text.toString()
        usu.apellido = ed_apellido.text.toString()
        usu.correo = ed_correo.text.toString()
        usu.username = ed_user.text.toString()
        usu.pass = ed_password.text.toString()

        progressBar = findViewById(R.id.Barra)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("usuarios")

        bt_crear.setOnClickListener {
            nueva_cuenta()
        }

        //val bd = room.getDatabase(this)
        //val bd:FirebaseFirestore = FirebaseFirestore.getInstance()
        //auth = FirebaseAuth.getInstance()
/*
        bt_crear.setOnClickListener{
            val usu=Usuarios()
            usu.nombre = ed_nombre.text.toString()
            usu.apellido = ed_apellido.text.toString()
            usu.correo = ed_correo.text.toString()
            usu.user = ed_username.text.toString()
            usu.pass = ed_password.text.toString()
            if (ed_nombre.text.isNotEmpty() && ed_apellido.text.isNotEmpty() && ed_username.text.isNotEmpty() && ed_password.text.isNotEmpty())
            {
                val usu1 = HashMap<String,Any>()

                usu1.put("nombre",ed_nombre.text.toString())
                usu1.put("apellido",ed_apellido.text.toString())
                usu1.put("correo",ed_correo.text.toString())
                usu1.put("user",ed_username.text.toString())
                usu1.put("pass",ed_password.text.toString())


                bd.collection("Usuarios")
                    .add(usu)
                    .addOnSuccessListener(OnSuccessListener {
                        Toast.makeText(this,"Usuario registrado",Toast.LENGTH_SHORT).show()
                    })
                    .addOnFailureListener(OnFailureListener {
                        Toast.makeText(this,"Error de registro.",Toast.LENGTH_SHORT).show()
                    })
                    finish()

            }
            else
            {
                Toast.makeText(this,"Campos obligatorios.",Toast.LENGTH_SHORT).show()
            }

        }
        */
    }

    private fun nueva_cuenta() {
        val nombre: String = ed_nombre.text.toString()
        val apellido: String = ed_apellido.text.toString()
        val correo: String = ed_correo.text.toString()
        val username:String = ed_user.text.toString()
        val pass: String = ed_password.text.toString()

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(pass)) {
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this) { task ->

                    if (task.isComplete) {
                        val user: FirebaseUser? = auth.currentUser

                        verificar_correo(user)

                        val userBD = dbReference.child(user?.uid.toString())
                        userBD.child("nombre").setValue(nombre)
                        userBD.child("apellido").setValue(apellido)
                        userBD.child("username").setValue(username)

                        //startActivity(Intent(this,MainActivity::class.java))
                        action_login()
                        Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun action_login() {
        val dialog = AlertDialog.Builder(this@CrearCuenta)
        val view = layoutInflater.inflate(R.layout.dialog, null)

        val etcorreo = view.findViewById<EditText>(R.id.edtCorreo)
        val etpass = view.findViewById<EditText>(R.id.edtPass)
        val btlog = view.findViewById<Button>(R.id.btnLogin)

        dialog.setView(view)
        dialog.setCancelable(true)

        auth = FirebaseAuth.getInstance()

        val dialogShow = dialog.create()
        dialogShow.show()

        btlog.setOnClickListener {
            val correo: String = etcorreo.text.toString()
            val pass: String = etpass.text.toString()

            if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(pass)) {

                auth.signInWithEmailAndPassword(correo, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Usted se ha logeado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Usuario o clave incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }

            } else {
                Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show()
            }
        }
        txt_restaurar.setOnClickListener {
            startActivity(Intent(this,ForgotPass::class.java))
        }
    }

    private fun verificar_correo(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this)
            { task ->
                if (task.isComplete) {
                    Toast.makeText(this, "Email enviado", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Error al enviar email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
