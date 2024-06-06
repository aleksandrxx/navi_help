package com.example.navi_help.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navi_help.MainActivity2
import com.example.navi_help.R

class AdapterBookmark(private val Org: List<String>,private val Adr: List<String>,val context: Context):
    RecyclerView.Adapter<AdapterBookmark.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textOrg: TextView = view.findViewById(R.id.textView_route)
        val textAdr: TextView = view.findViewById(R.id.textView_floor)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_activity_1_bookmark, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = Org.size

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.textOrg.text = Org[position]
        viewHolder.textAdr.text = Adr[position]

        viewHolder.itemView.setOnClickListener{
            val intent = Intent(context,MainActivity2::class.java)
            intent.putExtra("id",position)
            intent.putExtra("name",Org[position])
            context.startActivity(intent)
        }
    }
}