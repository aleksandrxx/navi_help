package com.example.navi_help

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navi_help.adapters.AdapterRouters
import com.example.navi_help.others.Alert
import java.util.Locale
import java.util.Objects


class MainActivity2 : AppCompatActivity() /* отслеживание диалога*/{

    private val REQUEST_CODE_SPEECH_INPUT_1 = 0
    private val REQUEST_CODE_SPEECH_INPUT_2 = 1
    lateinit var activityRouteBegEditText:EditText
    lateinit var activityRouteEndEditText:EditText
    lateinit var activityRecyclerRoute:RecyclerView

    val masRouteBegin = mutableListOf("вход","кабинет 213","вход","кабинет 213")
    val masRouteEnd = mutableListOf("кабинет 213","выход","кабинет 213 ИИ","выход ИИ")
    val masFloor = mutableListOf("2","2","2","2")
    val masTime = mutableListOf("Пн-Пт С 9:00 до 17:00","Пн-Пт С 9:00 до 17:00","Пн-Пт С 9:00 до 17:00","Пн-Пт С 9:00 до 17:00")
    val masTel = mutableListOf("+79089095311","+79089095311","+79089095311","+79089095311")
    val masSite = mutableListOf("http://192.168.0.1","http://192.168.0.1","http://192.168.0.1","http://192.168.0.1")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        activityRecyclerRoute = findViewById<RecyclerView>(R.id.route_recycler)
        activityRouteBegEditText = findViewById<EditText>(R.id.route_beg_editText)
        activityRouteEndEditText = findViewById<EditText>(R.id.route_end_editText)
        val activityVoiceImgButLeft = findViewById<ImageButton>(R.id.voice_imgBut1)
        val activityVoiceImgButRight = findViewById<ImageButton>(R.id.voice_imgBut2)
        val activityNameTextView = findViewById<TextView>(R.id.name_textview)
        val activityAdrTextView = findViewById<TextView>(R.id.adr_textview)

        val org_id = intent.getIntExtra("id",0)
        val org_name = intent.getStringExtra("name").toString()




        if (org_id != 0) {
            activityNameTextView.setText("УРГПУ Уральский государственный педагогический университет")
            activityAdrTextView.setText("просп. Космонавтов 26")
            Alert().dialogError(   // класс ErrorAlert папка others
                "Внимание","Выбранная вами организация:\n$org_name\nсейчаc недоступна",this)
        }//  Выводим ошибку если выбрана не РГППУ
        else {
            activityNameTextView.setText("РГППУ Российский государственный профессионально-педагогический университет")
            activityAdrTextView.setText("ул. Машиностроителей 11")
            loadRouters(activityRecyclerRoute,masRouteBegin,masRouteEnd,masFloor,masTime,masTel,masSite)
        }

        activityRouteBegEditText.setOnEditorActionListener { text, enter, _ ->
            if (enter == EditorInfo.IME_ACTION_DONE) {
                searchFilter(text.text,activityRouteEndEditText.text,masRouteBegin,masRouteEnd,masFloor,masTime,masTel,masSite,activityRecyclerRoute)
            }
            true
        }




        activityRouteEndEditText.setOnEditorActionListener { text, enter, _ ->
            if (enter == EditorInfo.IME_ACTION_DONE) {
                searchFilter(activityRouteBegEditText.text,text.text,masRouteBegin,masRouteEnd,masFloor,masTime,masTel,masSite,activityRecyclerRoute)
            }
            true
        }

        activityVoiceImgButLeft.setOnClickListener{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT_1)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message,Toast.LENGTH_SHORT).show()
            }
        }

        activityVoiceImgButRight.setOnClickListener{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT_2)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message,Toast.LENGTH_SHORT).show()
            }
        }




    }

    fun searchFilter(begin:CharSequence,end:CharSequence,masRouteBegin: MutableList<String>,masRouteEnd: MutableList<String>,masFloor: MutableList<String>,masTime: MutableList<String>,masTel: MutableList<String>,masSite: MutableList<String>,activityRecyclerRoute:RecyclerView){
        /*val q = masRouteBegin.map { masRouteBegin.indexOf(it) }
            .filter {masRouteBegin[it] == begin.toString().trim()}
        val arr1 = mutableListOf<String>()
        val arr2 = mutableListOf<String>()
        val arr3 = mutableListOf<String>()
        val arr4 = mutableListOf<String>()
        val arr5 = mutableListOf<String>()
        val arr6 = mutableListOf<String>()
        for (i in q ){
            arr1.add(masRouteBegin[i])
            arr2.add(masRouteEnd[i])
            arr3.add(masFloor[i])
            arr4.add(masTime[i])
            arr5.add(masTel[i])
            arr6.add(masSite[i])
        }

        loadRouters(activityRecyclerRoute,arr1,arr1,arr1,arr1,arr1,arr1)*/
        if (begin.isEmpty() && end.isEmpty())
            Toast.makeText(this, "оба пусты",Toast.LENGTH_SHORT).show()
        else if (!begin.isEmpty() && end.isEmpty()){
            val q = masRouteBegin.map { masRouteBegin.indexOf(it) }
                .filter {masRouteBegin[it] == begin.toString().trim()}
            val arr1 = mutableListOf<String>()
            val arr2 = mutableListOf<String>()
            val arr3 = mutableListOf<String>()
            val arr4 = mutableListOf<String>()
            val arr5 = mutableListOf<String>()
            val arr6 = mutableListOf<String>()
            for (i in q ){
                arr1.add(masRouteBegin[i])
                arr2.add(masRouteEnd[i])
                arr3.add(masFloor[i])
                arr4.add(masTime[i])
                arr5.add(masTel[i])
                arr6.add(masSite[i])
            }
            loadRouters(activityRecyclerRoute,arr1,arr2,arr3,arr4,arr5,arr6)
        }
        else if (begin.isEmpty() && !end.isEmpty()){
            val q = masRouteEnd.map { masRouteEnd.indexOf(it) }
                .filter {masRouteEnd[it] == end.toString().trim()}
            val arr1 = mutableListOf<String>()
            val arr2 = mutableListOf<String>()
            val arr3 = mutableListOf<String>()
            val arr4 = mutableListOf<String>()
            val arr5 = mutableListOf<String>()
            val arr6 = mutableListOf<String>()
            for (i in q ){
                arr1.add(masRouteBegin[i])
                arr2.add(masRouteEnd[i])
                arr3.add(masFloor[i])
                arr4.add(masTime[i])
                arr5.add(masTel[i])
                arr6.add(masSite[i])
            }
            loadRouters(activityRecyclerRoute,arr1,arr2,arr3,arr4,arr5,arr6)
        }
        else if (!begin.isEmpty() && !end.isEmpty()){
            val w = masRouteBegin.map { masRouteBegin.indexOf(it) }
                .filter {masRouteBegin[it] == begin.toString().trim() && masRouteEnd[it] == end.toString().trim()}

            val arr1 = mutableListOf<String>()
            val arr2 = mutableListOf<String>()
            val arr3 = mutableListOf<String>()
            val arr4 = mutableListOf<String>()
            val arr5 = mutableListOf<String>()
            val arr6 = mutableListOf<String>()
            for (i in w){
                arr1.add(masRouteBegin[i])
                arr2.add(masRouteEnd[i])
                arr3.add(masFloor[i])
                arr4.add(masTime[i])
                arr5.add(masTel[i])
                arr6.add(masSite[i])
            }

            loadRouters(activityRecyclerRoute,arr1,arr2,arr3,arr4,arr5,arr6)
        }

    }

    fun loadRouters(activityRecyclerRoute:RecyclerView,masRouteBegin: MutableList<String>,masRouteEnd: MutableList<String>,masFloor: MutableList<String>,masTime: MutableList<String>,masTel: MutableList<String>,masSite: MutableList<String>){
        activityRecyclerRoute.layoutManager = LinearLayoutManager(this)
        activityRecyclerRoute.adapter = AdapterRouters(masRouteBegin,masRouteEnd,masFloor,masTime,masTel,masSite, context = this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT_1) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                activityRouteBegEditText.setText( Objects.requireNonNull(res)[0])
                searchFilter(activityRouteBegEditText.text,activityRouteEndEditText.text,masRouteBegin,masRouteEnd,masFloor,masTime,masTel,masSite,activityRecyclerRoute)

            }
        }
        if (requestCode == REQUEST_CODE_SPEECH_INPUT_2) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                activityRouteEndEditText.setText( Objects.requireNonNull(res)[0])

                searchFilter(activityRouteBegEditText.text,activityRouteEndEditText.text,masRouteBegin,masRouteEnd,masFloor,masTime,masTel,masSite,activityRecyclerRoute)

            }
        }
    }

    //загрузка истории из бд
    /*fun download_story() {
        progressBar.setAlpha(1)
        states2.clear()
        val myThread_4: Thread = object : Thread() {
            override fun run() {
                val db: SQLiteDatabase = dbHelper.getReadableDatabase()
                val c = db.query("story", null, null, null, null, null, null)
                if (c.moveToFirst()) {
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
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )
                        )
                    } while (c.moveToNext())
                }
                c.close()
                db.close()

                runOnUiThread {
                    if (states2.size() === 0) {
                        textView_list_null_2.setAlpha(1)
                        text_clear_story.setAlpha(0)
                    } else if (states2.size() > 0) {
                        list_story.setAdapter(adapter_story)
                        textView_list_null_2.setAlpha(0)
                    }
                    progressBar.setAlpha(0) // Отмена анимации загрузки
                }
            }
        }
        myThread_4.start()
    }*/



    //загрузка организаций из сети (переделать)
    /* class Download_organiztion_Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading_view.setVisibility(View.VISIBLE);   // Запуск анимации загрузки
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            loading_view.setVisibility(View.INVISIBLE); // Отмена анимации загрузки
        }
        @Override
        protected Void doInBackground(Void... voids) {
           try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Context context = getApplicationContext();
            int duration=Toast.LENGTH_LONG;
            try {
                Document doc  = Jsoup.connect("http://192.168.1.5/html/building/get_table_building.php?street="+street_user.getText().toString().toLowerCase()+"").timeout(5000).get();
                td_id_building = doc.getElementsByClass("td_id_building");
                td_street_building = doc.getElementsByClass("td_street_building");
                td_number_building = doc.getElementsByClass("td_number_building");
                td_building_building = doc.getElementsByClass("td_building_building");
                td_organization_building = doc.getElementsByClass("td_organization_building");
                building_id.clear();
                street_building.clear();
                number_building.clear();
                building_building.clear();
                organization_building.clear();
                for (int i = 0; i< td_street_building.size(); i++)
                {
                    building_id.add(i, td_id_building.get(i).text());
                    street_building.add(i, td_street_building.get(i).text());
                    number_building.add(i, td_number_building.get(i).text());
                    building_building.add(i, td_building_building.get(i).text());
                    organization_building.add(i, td_organization_building.get(i).text());
                }
                *//*Set<String> set = new HashSet<>(address); //Убрать повторяющиеся
                address.clear();
                address.addAll(set);*//*
                *//*address.add(0,"Выберите адрес");*//*
            } catch (MalformedURLException e) {
                dialog.show(getSupportFragmentManager(), "Проблема соединения с сервером: 1 \n\n1. Проверьте доступ в интернет \n2. Зайдите на сайт ... и если к нему нет доступа попробуйте воспользоваться приложением позже \n\nПодробнее: \n"+String.valueOf(e));
                organiztion_Task.cancel(true);
            } catch (HttpStatusException e) {
                dialog.show(getSupportFragmentManager(), "Проблема соединения с сервером: 2 \n\n1. Проверьте доступ в интернет \n2. Зайдите на сайт ... и если к нему нет доступа попробуйте воспользоваться приложением позже \n\nПодробнее: \n"+String.valueOf(e));
                organiztion_Task.cancel(true);
            } catch (UnsupportedMimeTypeException e) {
                dialog.show(getSupportFragmentManager(), "Проблема соединения с сервером: 3 \n\n1. Проверьте доступ в интернет \n2. Зайдите на сайт ... и если к нему нет доступа попробуйте воспользоваться приложением позже \n\nПодробнее: \n"+String.valueOf(e));
                organiztion_Task.cancel(true);
            }catch (SocketTimeoutException e) {
                dialog.show(getSupportFragmentManager(), "Проблема соединения с сервером: 4 \n\n1. Проверьте доступ в интернет \n2. Зайдите на сайт ... и если к нему нет доступа попробуйте воспользоваться приложением позже \n\nПодробнее: \n"+String.valueOf(e));
                organiztion_Task.cancel(true);
            } catch (IOException e) {
                *//*String msg = String.valueOf(e)+" "+5;
                Toast.makeText(context, msg, duration).show();*//*
                dialog.show(getSupportFragmentManager(), "Проблема соединения с сервером: 5 \n\n1. Проверьте доступ в интернет \n2. Зайдите на сайт ... и если к нему нет доступа попробуйте воспользоваться приложением позже \n\nПодробнее: \n"+String.valueOf(e));
                organiztion_Task.cancel(true);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (street_building.size()!=0){
                Collections.sort(street_building);
                for (int i = 0; i< street_building.size(); i++)
                {
                    states2.add(new State2 (organization_building.get(i),street_building.get(i), number_building.get(i),building_building.get(i),building_id.get(i)));
                }
            }
            else {
                states2.add(new State2 ("Результатов по запросу: "+street_user.getText()+" не найдено", "","","",""));
            }
            // устанавливаем для списка адаптер
            recyclerView.setAdapter(adapter);
            constraint_layout.setBackgroundColor(Color.parseColor("#F0F0F0"));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    list_result_search.getLayoutParams().MATCH_PARENT,
                    list_result_search.getLayoutParams().MATCH_PARENT,
                    1.0f
            );
            list_result_search.setLayoutParams(param);
            loading_view.setVisibility(View.INVISIBLE); // Отмена анимации загрузки
        }
    }*/
}


