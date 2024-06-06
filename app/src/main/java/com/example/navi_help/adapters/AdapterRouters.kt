package com.example.navi_help.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navi_help.MainActivity3
import com.example.navi_help.MainActivity4
import com.example.navi_help.MainActivity5
import com.example.navi_help.R

class AdapterRouters(private val Route1: List<String>,private val Route2: List<String>, private val Floor: List<String>, private val Time: List<String>, private val Tel: List<String>, private val Site: List<String>, val context: Context):
    RecyclerView.Adapter<AdapterRouters.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Route: TextView = view.findViewById(R.id.textView_route)
        val Floor: TextView = view.findViewById(R.id.textView_floor)
        val Time: TextView = view.findViewById(R.id.textView_time)
        val Tel: ImageButton = view.findViewById(R.id.tel_route_imgBut)
        val Site: ImageButton = view.findViewById(R.id.site_route_imgBut)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_activity_2_route, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = Route1.size

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.Route.text = Route1[position]+" -> "+ Route2[position]
        viewHolder.Floor.text = Floor[position]+" этаж"
        viewHolder.Time.text = Time[position]

        viewHolder.Tel.setOnClickListener{
            val i = Intent(Intent.ACTION_DIAL)
            i.data = Uri.parse("tel:" + Tel[position])
            context.startActivity(i)
        }

        viewHolder.Site.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(Site[position])
            context.startActivity(i)
        }
        viewHolder.itemView.setOnClickListener{
            if (position<2){
                val intent = Intent(context, MainActivity4::class.java)
                intent.putExtra("id",position)
                intent.putExtra("route1",Route1[position])
                intent.putExtra("route2",Route2[position])
                context.startActivity(intent)
            }
            else{
                val intent = Intent(context, MainActivity5::class.java)
                intent.putExtra("id",position)
                intent.putExtra("route1",Route1[position])
                intent.putExtra("route2",Route2[position])
                context.startActivity(intent)
            }


        }
    }
}