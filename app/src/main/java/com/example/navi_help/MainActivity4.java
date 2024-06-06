package com.example.navi_help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    private BluetoothAdapter bt_adapter;
    private final int bt_ACCESS_COARSE_LOCATION = 0;
    private final int bt_ACCESS_BLUETOOTH_SCAN = 1;
    private final int bt_ACCESS_FINE_LOCATION = 2;
    private final int bt_BLUETOOTH_CONNECT = 3;
    List<String> elemnts_ble_mac = new ArrayList<>();
    int[] rssi = new int[3];
    int a1=0;int a2=0;int a3=0;
    boolean route_begin=false;
    int step = 1;
    int[] ble_1_rssi = new int[6];
    int[] ble_2_rssi = new int[6];
    int[] ble_3_rssi = new int[6];

    int percent=-10;
    boolean[][] start= new boolean[12][16];
    boolean[][] start_sr= new boolean[12][16];
    boolean[][] sr= new boolean[12][16];
    boolean[][] sr_finish= new boolean[12][16];
    boolean[][] finish= new boolean[12][16];
    boolean[][] now= new boolean[12][16];
    TableLayout activityTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        bt_adapter = BluetoothAdapter.getDefaultAdapter();

        activityTableLayout = findViewById(R.id.tablelayout);
        ImageButton activityCloseMapBut = findViewById(R.id.close_map_imgBut);
        TextView activityRouteBegTextView = findViewById(R.id.route_beg_textView);
        TextView activityRouteEndTextView = findViewById(R.id.route_end_textView);

        elemnts_ble_mac.add("C9:9A:FB:95:FA:3A");
        elemnts_ble_mac.add("F6:CD:8A:11:A7:89");
        elemnts_ble_mac.add("F6:E3:11:E6:9C:C9");

        Integer route_id = getIntent().getIntExtra("id",0);
        activityRouteBegTextView.setText(getIntent().getStringExtra("route1"));
        activityRouteEndTextView.setText(getIntent().getStringExtra("route2"));

        activityCloseMapBut.setOnClickListener(view -> finish());

       /* activityTableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));*/


        if (route_id==0){
            start[11][9]=true;
            start_sr[10][9]=true;
            sr_finish[8][9]=true;
            finish[7][9]=true;
        }
        if (route_id==1){
            start[7][9]=true;
            start_sr[8][9]=true;
            sr_finish[10][9]=true;
            finish[11][9]=true;
        }
        sr[9][9]=true;





        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, bt_ACCESS_FINE_LOCATION);
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, bt_ACCESS_COARSE_LOCATION);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S & ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, bt_BLUETOOTH_CONNECT);
                }
                else {
                    route_begin=true;
                    search_bl();
                    run_route();
                }
            }
        }



    }
    private void run_route(){
        new Thread(() -> {
            while (route_begin){
                if (step==1){
                    ble_1_rssi[0]=rssi[0];ble_2_rssi[0]=rssi[1];ble_3_rssi[0]=rssi[2];
                    step++;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (step==2){
                    ble_1_rssi[1]=rssi[0];ble_2_rssi[1]=rssi[1];ble_3_rssi[1]=rssi[2];
                    step++;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (step==3){
                    ble_1_rssi[2]=rssi[0];ble_2_rssi[2]=rssi[1];ble_3_rssi[2]=rssi[2];
                    step++;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (step==4){
                    ble_1_rssi[3]=rssi[0];ble_2_rssi[3]=rssi[1];ble_3_rssi[3]=rssi[2];
                    step++;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (step==5){
                    ble_1_rssi[4]=rssi[0];ble_2_rssi[4]=rssi[1];ble_3_rssi[4]=rssi[2];
                    step++;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (step==6){
                    step=1;
                    ble_1_rssi[5]=rssi[0];ble_2_rssi[5]=rssi[1];ble_3_rssi[5]=rssi[2];
                    double average_1=999;
                    /*if (ble_1_rssi[0]!=0 || ble_1_rssi[1]!=0 || ble_1_rssi[2]!=0 || ble_1_rssi[3]!=0 || ble_1_rssi[4]!=0 || ble_1_rssi[5]!=0){*/
                    average_1=(ble_1_rssi[0]+ble_1_rssi[1]+ble_1_rssi[2]+ble_1_rssi[3]+ble_1_rssi[4]+ble_1_rssi[5])/6*-1;
                    average_1=10*((60-average_1)/(10*2));

                    double average_2=999;
                    /*if (ble_2_rssi[0]!=0 || ble_2_rssi[1]!=0 || ble_2_rssi[2]!=0 || ble_2_rssi[3]!=0 || ble_2_rssi[4]!=0 || ble_2_rssi[5]!=0){*/
                    average_2=(ble_2_rssi[0]+ble_2_rssi[1]+ble_2_rssi[2]+ble_2_rssi[3]+ble_2_rssi[4]+ble_2_rssi[5])/6*-1;
                    average_2=10*((60-average_2)/(10*2));

                    double average_3=999;
                    /*if (ble_3_rssi[0]!=0 || ble_3_rssi[1]!=0 || ble_3_rssi[2]!=0 || ble_3_rssi[3]!=0 || ble_3_rssi[4]!=0 || ble_3_rssi[5]!=0){*/
                    average_3=(ble_3_rssi[0]+ble_3_rssi[1]+ble_3_rssi[2]+ble_3_rssi[3]+ble_3_rssi[4]+ble_3_rssi[5])/6*-1;
                    average_3=10*((60-average_3)/(10*2));

                    double finalAverage_ = average_1;
                    double finalAverage_1 = average_2;
                    double finalAverage_2 = average_3;

                    Log.v("s","average_1 "+average_1+" average_2 "+average_2+" average_3 "+average_3);





                    /*if (finalAverage_ > finalAverage_1 & finalAverage_ > finalAverage_2){
                        Double w1 =  (finalAverage_ + finalAverage_1)/2;
                        w1=(finalAverage_+w1)/2;
                        if (finalAverage_<w1)
                            now=start;
                        else
                            now =start_sr;
                    }
                    else if (finalAverage_1 > finalAverage_ & finalAverage_1 > finalAverage_2){
                        Double w1 =  (finalAverage_ + finalAverage_1)/2;
                        w1=(finalAverage_+w1)/2;
                        Double w2 =  (finalAverage_1 + finalAverage_2)/2;
                        w2=(finalAverage_1+w2)/2;
                        if (finalAverage_1 <w1)
                            now =start_sr;
                        else if (finalAverage_1 >w1 & finalAverage_1<w2) {
                            now=sr;
                        }
                        else{
                            now=sr_finish;
                        }

                    }
                    else if (finalAverage_2 > finalAverage_ & finalAverage_2 > finalAverage_1){
                        Double w1 =  (finalAverage_1 + finalAverage_2)/2;
                        w1=(finalAverage_2+w1)/2;
                        if (finalAverage_2<w1)
                            now=sr_finish;
                        else
                            now =finish;
                    }*/

                    if (finalAverage_ > finalAverage_1 & finalAverage_ > finalAverage_2){
                        now=start;
                    }
                    else if (finalAverage_1 > finalAverage_ & finalAverage_1 > finalAverage_2){
                        now=sr;
                    }
                    else if (finalAverage_2 > finalAverage_ & finalAverage_2 > finalAverage_1){
                        now=finish;
                    }

                    boolean[][] final_now= now;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            activityTableLayout.removeAllViews();
                            for (int i = 0; i < 12; i++) {
                                TableRow tableRow = new TableRow(MainActivity4.this);
                                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT));
                                tableRow.setGravity(Gravity.CENTER);
                                for (int p = 0; p < 16; p++) {
                                    if (final_now[i][p]){
                                        TextView cell = new TextView(MainActivity4.this);
                                        cell.setText("");
                                        cell.setBackgroundResource(R.drawable.but_yellow_round);
                                        cell.setTextSize(24f);
                                        cell.setGravity(Gravity.CENTER);
                                        tableRow.addView(cell);
                                    }
                                    if (start[i][p]){
                                        TextView cell = new TextView(MainActivity4.this);
                                        cell.setText("");
                                        cell.setBackgroundResource(R.drawable.ic_start_green);
                                        cell.setTextSize(24f);
                                        cell.setGravity(Gravity.CENTER);
                                        tableRow.addView(cell);
                                    }
                                    if (finish[i][p]){
                                        TextView cell = new TextView(MainActivity4.this);
                                        cell.setText("");
                                        cell.setBackgroundResource(R.drawable.ic_start_blue);
                                        cell.setTextSize(24f);
                                        cell.setGravity(Gravity.CENTER);
                                        tableRow.addView(cell);
                                    }
                                    else {
                                        TextView cell = new TextView(MainActivity4.this);
                                        cell.setText("");
                                        cell.setBackgroundResource(android.R.drawable.screen_background_light_transparent);
                                        cell.setTextSize(24f);
                                        cell.setGravity(Gravity.CENTER);
                                        tableRow.addView(cell);
                                    }
                                }
                                activityTableLayout.addView(tableRow);
                                activityTableLayout.setStretchAllColumns(true);
                                activityTableLayout.setShrinkAllColumns(true);
                            }
                        }
                    });
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }).start();
    }

    @SuppressLint("MissingPermission")
    private void search_bl() {
        new Thread(() -> {
            BluetoothLeScanner scanner = bt_adapter.getBluetoothLeScanner();
            String[] names = new String[]{"HC-42"};
            List<ScanFilter> filters = new ArrayList<>();
            if (names != null) {
                for (String name : names) {
                    ScanFilter filter = new ScanFilter.Builder()
                            .setDeviceName(name)
                            .build();
                    filters.add(filter);
                }
            }
            ScanSettings scanSettings = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scanSettings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                        .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                        .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                        .setReportDelay(0L)
                        .build();
            } else {
            }

            final ScanCallback scanCallback = new ScanCallback() {
                @SuppressLint("MissingPermission")
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    String a = result.getDevice().getAddress();
                    int r = result.getRssi();

                    if (a.equals(elemnts_ble_mac.get(0))) {
                        rssi[0] = r;
                        a1++;
                    }
                    if (a.equals(elemnts_ble_mac.get(1))) {
                        rssi[1] = r;
                        a2++;
                    }
                    if (a.equals(elemnts_ble_mac.get(2))) {
                        rssi[2] = r;
                        a3++;
                    }
                }
            };
            if (scanner != null) {
                scanner.startScan(filters, scanSettings, scanCallback);
            }
        }).start();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch ( requestCode ) {
            case bt_ACCESS_COARSE_LOCATION: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] != PackageManager.PERMISSION_GRANTED ) {
                        Toast.makeText(this,"Для этого приложения требуется разрешение на гелокацию", Toast.LENGTH_LONG).show();
                    }
                }
            }
            case bt_ACCESS_BLUETOOTH_SCAN: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] != PackageManager.PERMISSION_GRANTED ) {
                        Toast.makeText(this,"Для этого приложения требуется разрешение на поиск Bluetooth", Toast.LENGTH_LONG).show();
                    }
                }
            }
            case bt_ACCESS_FINE_LOCATION: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] != PackageManager.PERMISSION_GRANTED ) {
                        Toast.makeText(this,"Для этого приложения требуется разрешение на гелокацию", Toast.LENGTH_LONG).show();
                    }
                }
            }
            case bt_BLUETOOTH_CONNECT: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] != PackageManager.PERMISSION_GRANTED ) {
                        Toast.makeText(this,"Для этого приложения требуется разрешение на поиск Bluetoot", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}