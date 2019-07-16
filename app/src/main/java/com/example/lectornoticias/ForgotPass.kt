package com.example.lectornoticias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPass : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        auth=FirebaseAuth.getInstance()

        btn_send_pass.setOnClickListener {
            val email=edt_Correo.text.toString()

            if(!TextUtils.isEmpty(email)){
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this){
                        task ->
                        if (task.isSuccessful){
                            pb_forgotPass.visibility= View.VISIBLE
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                        else{
                            Toast.makeText(this,"Error al enviar correo",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


}
