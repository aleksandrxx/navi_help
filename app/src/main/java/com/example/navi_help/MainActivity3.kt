package com.example.navi_help

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity3 : AppCompatActivity() {

    private val deviceList = ArrayList<BluetoothDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        /*Alert().dialogError(   // класс ErrorAlert папка others
            "Внимание","Маршрут завершен\nвы на месте",this)*/

        val activityTableLayout = findViewById<TableLayout>(R.id.tablelayout)
        val activityCloseMapBut = findViewById<ImageButton>(R.id.close_map_imgBut)
        val activityRouteBegTextView = findViewById<TextView>(R.id.route_beg_textView)
        val activityRouteEndTextView = findViewById<TextView>(R.id.route_end_textView)

        val route_id = intent.getIntExtra("id",0)
        activityRouteBegTextView.text = intent.getStringExtra("route1")
        activityRouteEndTextView.text = intent.getStringExtra("route2")



        activityCloseMapBut.setOnClickListener{
            finish()
        }
        activityTableLayout.isStretchAllColumns=true
        activityTableLayout.isShrinkAllColumns=true
        activityTableLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val start = Array(12) { BooleanArray(16)}
        val finish = Array(12) { BooleanArray(16)}
        val now = Array(12) { BooleanArray(16)}
        if (route_id==0){
            start[11][9]=true;
            finish[5][9]=true;
        }
        if (route_id==1){
            start[5][9]=true;
            finish[11][9]=true;
        }





        for(i in 0..11){    //  строки
            val row = TableRow(this).apply {
                layoutParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER
            }
            for(p in 0..15){    //  ячейки
                if (now[i][p]){
                    val cell = TextView(this).apply {
                        text = ""
                        setBackgroundResource(R.drawable.but_yellow_round)
                        textSize = 24f
                        gravity = Gravity.CENTER
                    }
                    row.addView(cell)
                }
                if (start[i][p]){
                    val cell = TextView(this).apply {
                        text = ""
                        setBackgroundResource(R.drawable.ic_start_green)
                        textSize = 24f
                        gravity = Gravity.CENTER
                    }
                    row.addView(cell)
                }
                if (finish[i][p]){
                    val cell = TextView(this).apply {
                        text = ""
                        setBackgroundResource(R.drawable.ic_start_blue)
                        textSize = 24f
                        gravity = Gravity.CENTER
                    }
                    row.addView(cell)
                }
                else{
                    val cell = TextView(this).apply {
                        text = ""
                        setBackgroundResource(android.R.drawable.screen_background_light_transparent)
                        textSize = 24f
                        gravity = Gravity.CENTER
                    }
                    row.addView(cell)
                }

            }
            activityTableLayout.addView(row);
        }
    }


}
