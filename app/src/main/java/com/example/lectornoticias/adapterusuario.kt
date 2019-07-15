package com.example.lectornoticias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.itemrecyclerusuarios.view.*

class Adapterusuario(val listausuarios:MutableList<DocumentSnapshot>):RecyclerView.Adapter<Adapterusuario.Viewholder>()
{
    val bd = FirebaseFirestore.getInstance()
    class Viewholder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
        //val tvuser:TextView=itemview.tvuser
        val tvnombre:TextView=itemview.tvnombre
        val tvapellido:TextView=itemview.tvapellido
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapterusuario.Viewholder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.itemrecyclerusuarios,p0,false)
        return  Viewholder(v)
    }

    override fun getItemCount(): Int {
        return listausuarios.size
    }

    override fun onBindViewHolder(p0: Adapterusuario.Viewholder, p1: Int) {
        p0.tvnombre.text = listausuarios[p1].get("nombre").toString()
        p0.tvapellido.text = listausuarios[p1].get("apellido").toString()

        p0.itemView.setOnClickListener{
            Toast.makeText(p0.itemView.context,p0.tvnombre.text.toString(),Toast.LENGTH_SHORT).show()
        }

    }
}