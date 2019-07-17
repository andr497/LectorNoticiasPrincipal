package com.example.lectornoticias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.itemrecyclernoticia.view.*

class Adapternoticia(val listanoticia:MutableList<DataSnapshot>?): RecyclerView.Adapter<Adapternoticia.Viewholder>()
{
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    class Viewholder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
        //val tvuser:TextView=itemview.tvuser
        val autor: TextView =itemview.tv_autor_item
        val titulo:TextView=itemview.tv_titulo_item
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapternoticia.Viewholder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.itemrecyclernoticia,p0,false)
        return  Viewholder(v)
    }

    override fun getItemCount(): Int {
        var size:Int = 0
        if (listanoticia != null) {
             size = listanoticia.size
        }
        return size
    }

    override fun onBindViewHolder(p0: Adapternoticia.Viewholder, p1: Int) {

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.getReference("noticias")


        p0.autor.text = listanoticia?.get(p1)?.child("autor").toString()
        p0.titulo.text = listanoticia?.get(p1)?.child("titulo").toString()

        p0.itemView.setOnClickListener{
            Toast.makeText(p0.itemView.context,p0.titulo.text.toString(),Toast.LENGTH_SHORT).show()
        }

    }
}