package com.example.navi_help

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navi_help.adapters.AdapterBookmark


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityRecyclerBookmark = findViewById<RecyclerView>(R.id.bookmark_recycler)   //  Cписок закладок
        val activityTextClearBookmark = findViewById<TextView>(R.id.text_clear_bookmark)    //  Кнопка очистки списка
        val activityTextNullBookmark = findViewById<TextView>(R.id.text_null_bookmark)      //  Надпись при пустом списке
        val activityButLeft = findViewById<ConstraintLayout>(R.id.l_mid_layout)             //  Кнопка поиска
        val activityButRight = findViewById<ConstraintLayout>(R.id.r_mid_layout)            //  Кнопка закладки
        val activityImgSearch = findViewById<ImageView>(R.id.imageView)                     //  Картинка поиска
        val activityImgBookmark = findViewById<ImageView>(R.id.imageView2)                  //  Картинка закладок

        val masBookmarkOrg = mutableListOf("РГППУ Российский государственный профессионально-педагогический университет","УРГПУ Уральский государственный педагогический университет")
        val masBookmarkAdr = mutableListOf("ул. Машиностроителей 11","просп. Космонавтов 26")

        // кнопка очистить закладки
        activityTextClearBookmark.setOnClickListener {
            masBookmarkOrg.clear()  //  Очищаем массив названий организаций
            masBookmarkAdr.clear()  //  Очищаем массив адреса организаций
            //  фугкция загрузка закладок
            loadBookmark(activityRecyclerBookmark,masBookmarkOrg,masBookmarkAdr,activityTextClearBookmark,activityTextNullBookmark)
            Toast.makeText(this,"Закладки были удалены",Toast.LENGTH_SHORT).show()
        }

        // кнопка поиск
        activityButLeft.setOnClickListener{
            Toast.makeText(this,"Данная функция недоступна, воспользуйтсь вкладкой Закладки",Toast.LENGTH_LONG).show()
        }

        // кнопка загрузка закладок
        activityButRight.setOnClickListener{
            //  фугкция загрузка закладок
            loadBookmark(activityRecyclerBookmark,masBookmarkOrg,masBookmarkAdr,activityTextClearBookmark,activityTextNullBookmark)
            activityButLeft.setBackgroundResource(R.drawable.but_white_round)
            activityButRight.setBackgroundResource(R.drawable.but_yellow_round)
            activityImgSearch.setImageResource(R.drawable.ic_search_grey)
            activityImgBookmark.setImageResource(R.drawable.ic_bookmark_dark)
        }

    }

    // загрузка закладок
    fun loadBookmark(activityRecyclerBookmark:RecyclerView, masBookmarkOrg:  MutableList<String>, masBookmarkAdr: MutableList<String>,activityTextClearBookmark: TextView,activityTextNullBookmark:TextView){
        activityRecyclerBookmark.layoutManager = LinearLayoutManager(this)
        activityRecyclerBookmark.adapter = AdapterBookmark(masBookmarkOrg,masBookmarkAdr, context = this)
        if (!masBookmarkOrg.isEmpty()) {                             //  если не пустой
            activityTextClearBookmark.visibility = View.VISIBLE         //  кнопка очистить
            activityTextNullBookmark.visibility = View.INVISIBLE          //  надпись список пуст
        }
        else {
            activityTextClearBookmark.visibility = View.INVISIBLE
            activityTextNullBookmark.visibility = View.VISIBLE
        }
    }

    //загрузка закладок из бд SQLiteDatabase (переделать)
    /*private open fun load_bookmark() {
        progressBar.setAlpha(1) // Запуск анимации загрузки
        textView_bookmark.setTextColor(ContextCompat.getColor(applicationContext, R.color.dark))
        textView_search.setTextColor(ContextCompat.getColor(applicationContext, R.color.gray))
        image_bookmark.setImageResource(R.drawable.icon_bookmark_dark)
        image_search.setImageResource(R.drawable.icon_search_grey)
        *//*main_layout_2.transitionToEnd();*//*tab_layout.transitionToEnd()
        val myThread_1: Thread = object : Thread() {
            override fun run() {
                states2.clear()
                //bd_start
                // создаем объект для данных
                val cv = ContentValues()
                // подключаемся к БД
                val db: SQLiteDatabase = dbHelper.getReadableDatabase()
                // подготовим данные для вставки в виде пар: наименование столбца - значение
                *//*cv.put("id_bookmark", 1);
                cv.put("organization_bookmark", "ргппу");
                cv.put("organization_1_bookmark", "общага");
                cv.put("city_bookmark", "екб");
                cv.put("street_bookmark", "каширская");
                cv.put("num_street_bookmark", "21");
                cv.put("num_building_bookmark", "55");
                cv.put("bookmark_bookmark", 0);
                db.insert("bookmark", null, cv);

                cv.put("id_bookmark", 2);
                cv.put("organization_bookmark", "ргппу");
                cv.put("organization_1_bookmark", "универ");
                cv.put("city_bookmark", "екб");
                cv.put("street_bookmark", "маш");
                cv.put("num_street_bookmark", "15");
                cv.put("num_building_bookmark", "25");
                cv.put("bookmark_bookmark", 0);
                db.insert("bookmark", null, cv);*//*

                // делаем запрос всех данных из таблицы bookmark, получаем Cursor
                val c = db.query("bookmark", null, null, null, null, null, null)
                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                    val id_ColIndex = c.getColumnIndex("id")
                    val id_bookmark_ColIndex = c.getColumnIndex("id_bookmark")
                    val organization_bookmark_ColIndex = c.getColumnIndex("organization_bookmark")
                    val organization_1_bookmark_ColIndex =
                        c.getColumnIndex("organization_1_bookmark")
                    val city_bookmark_ColIndex = c.getColumnIndex("city_bookmark")
                    val street_bookmark_ColIndex = c.getColumnIndex("street_bookmark")
                    val num_street_bookmark_ColIndex = c.getColumnIndex("num_street_bookmark")
                    val num_building_bookmark_ColIndex = c.getColumnIndex("num_building_bookmark")
                    val bookmark_bookmark_ColIndex = c.getColumnIndex("bookmark_bookmark")
                    do {
                        // получаем значения по номерам столбцов
                        states2.add(
                            State_2_org(
                                c.getString(organization_bookmark_ColIndex),
                                c.getString(organization_1_bookmark_ColIndex),
                                c.getString(city_bookmark_ColIndex),
                                c.getString(street_bookmark_ColIndex),
                                c.getInt(num_street_bookmark_ColIndex),
                                c.getString(num_building_bookmark_ColIndex),
                                c.getInt(id_bookmark_ColIndex),
                                0,
                                "0",
                                "0",
                                "0",
                                "0",
                                "",
                                ""
                            )
                        )
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext())
                }
                c.close()
                db.close()
                runOnUiThread {
                    if (states2.size() === 0) {
                        textView_list_null.setAlpha(1)
                    } else if (states2.size() > 0) {
                        list_bookmark.setAdapter(adapter_bookmark)
                        textView_list_null.setAlpha(0)
                    }
                    progressBar.setAlpha(0) // Отмена анимации загрузки
                }
            }
        }
        myThread_1.start()
    }*/

    //сохранение выбранной организации в историю (переделать)
    /*fun add_story(
        id: Int,
        organization: String?,
        organization_1: String?,
        city: String?,
        street: String?,
        num_street: Int,
        num_building: String?,
        bookmark: Int
    ) {
        val myThread_2: Thread = object : Thread() {
            override fun run() {
                val db: SQLiteDatabase = dbHelper.getWritableDatabase()
                val c = db.query("story", null, null, null, null, null, null)
                var bookmark_exists = false
                if (c.moveToFirst()) {
                    val id_bookmark_ColIndex = c.getColumnIndex("id_bookmark")
                    do {
                        if (c.getInt(id_bookmark_ColIndex) == id) {
                            bookmark_exists = true
                        }
                    } while (c.moveToNext())
                }
                if (!bookmark_exists) {
                    val cv = ContentValues()
                    cv.put("id_bookmark", id)
                    cv.put("organization_bookmark", organization)
                    cv.put("organization_1_bookmark", organization_1)
                    cv.put("city_bookmark", city)
                    cv.put("street_bookmark", street)
                    cv.put("num_street_bookmark", num_street)
                    cv.put("num_building_bookmark", num_building)
                    cv.put("bookmark_bookmark", bookmark)
                    db.insert("story", null, cv)
                    cv.clear()
                }
                db.close()
            }
        }
        myThread_2.start()
    }*/


}