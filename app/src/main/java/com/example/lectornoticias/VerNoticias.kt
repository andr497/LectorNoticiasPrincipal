package com.example.lectornoticias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_ver_noticias.*



class VerNoticias : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    var listanoticia:MutableList<DataSnapshot>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_noticias)

        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.getReference("noticias")
/*
        dbReference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val noti = p0.getValue(Noticias::class.java)
                if (noti != null) {
                    listanoticia?.addAll(p0.children)
                    rvnoticia.layoutManager= LinearLayoutManager(this@VerNoticias, LinearLayout.VERTICAL,false)
                    var adapter=Adapternoticia(listanoticia)
                    rvnoticia.adapter=adapter
                }
            }
        })*/


        rvnoticia.layoutManager = LinearLayoutManager(this)

        val option = FirebaseRecyclerOptions.Builder<Noticias>()
            .setQuery(dbReference,Noticias::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebase_recycler = object:
        FirebaseRecyclerAdapter<Noticias,MyViewHolder>(option){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@VerNoticias).inflate(R.layout.itemrecyclernoticia,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, p1: Int, p2: Noticias) {
                val placeid = getRef(p1).key.toString()

                dbReference.child(placeid).addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@VerNoticias, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        holder.autor.setText(p2.autor)
                        holder.titulo.setText(p2.titulo)

                        holder.itemView.setOnClickListener {
                            val dialog = AlertDialog.Builder(this@VerNoticias)
                            val view = layoutInflater.inflate(R.layout.dialog_noticia, null)

                            val titulo=view.findViewById<TextView>(R.id.lb_titulo)
                            val descripcion=view.findViewById<TextView>(R.id.lb_descripcion)
                            val autor=view.findViewById<TextView>(R.id.lb_autor)

                            dialog.setView(view)
                            dialog.setCancelable(true)

                            titulo.text = p2.titulo
                            descripcion.text = p2.descripcion
                            autor.text = p2.autor

                            val dialogShow = dialog.create()
                            dialogShow.show()
                        }

                    }
                })

                holder.delete.setOnClickListener{
                    dbReference.child(placeid).removeValue()
                }
            }

        }
        rvnoticia.adapter = firebase_recycler
        firebase_recycler.startListening()
    }
    class MyViewHolder(itemView: View?):
            RecyclerView.ViewHolder(itemView!!){
        internal var autor:TextView = itemView!!.findViewById<TextView>(R.id.tv_autor_item)
        internal var titulo:TextView=itemView!!.findViewById<TextView>(R.id.tv_titulo_item)
        internal var delete:ImageView=itemView!!.findViewById<ImageView>(R.id.iv_delete)
    }
}
