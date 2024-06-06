package com.example.navi_help.others

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Alert{
    fun dialogError(AlertTitle:String, AlertMessage:String, activity: AppCompatActivity){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(AlertTitle)
            .setMessage(AlertMessage)
            .setPositiveButton("Ок") { dialog, which ->
                activity.finish()
                    //.onDialogClosed()   // Вызываем метод закрытия Activity
            }
            .create().show()
    }
}