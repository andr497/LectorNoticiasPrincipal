package com.example.lectornoticias

import android.widget.EditText

/*class Usuarios()
{
    lateinit var nombre:String
    lateinit var apellido:String
    lateinit var username:String
}*/
data class Usuarios(val nombre: String? = null, val apellido: String? = null, val username: String? = null) {

    //override fun toString() = name + "\t" + subject + "\t" + mark
}