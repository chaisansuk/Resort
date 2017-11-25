package com.example.manitch.resortct.main_page;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;


import com.example.manitch.resortct.R;
import com.example.manitch.resortct.adapter.CustomAdapter;
import com.example.manitch.resortct.config.Config;
import com.example.manitch.resortct.grtservice_okhttp.GetService;
import com.example.manitch.resortct.main_home.MainActivityHome;
import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ManitCh on 11/20/2017.
 */

public class PageMain extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    // Refresh menu item
    private MenuItem refreshMenuItem;
    private CalendarPickerView calendar;
    Calendar c = Calendar.getInstance();
    int cday, cmonth, cyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        // shereoreference
        sharedPreferences = getApplicationContext().getSharedPreferences("session_member", Context.MODE_PRIVATE);
        // editText1
        EditText editText = (EditText)findViewById(R.id.editText);

        //tabhost
        TabHost tabHost = (TabHost) this.findViewById (R.id.tabhost);
        tabHost.setup ( );
        TabHost.TabSpec tab_roomstatus = tabHost.newTabSpec ("RoomStatus");
        tab_roomstatus.setIndicator ("RoomStatus");
        tab_roomstatus.setContent (R.id.roomstatus);



        tabHost.addTab (tab_roomstatus);

        TabHost.TabSpec tab_book = tabHost.newTabSpec ("Booking");
        tab_book.setIndicator ("Booking");
        tab_book.setContent (R.id.booking);
       // tab_book.setIndicator("Booking",getResources().getDrawable(R.drawable.internet1));
        tabHost.addTab (tab_book);
        // calendar
//        Calendar nextYear = Calendar.getInstance();
//        nextYear.add(Calendar.YEAR,1);
//        calendar = (CalendarPickerView) findViewById(R.id.calendar);
//        Date today = new Date();
//        calendar.init(today, nextYear.getTime())
//                .withSelectedDate(today)
//                .inMode(CalendarPickerView.SelectionMode.RANGE);
        //calendar.highlightDates(getHolidays());

        TabWidget tabWidget = tabHost.getTabWidget ();
        tabWidget.setEnabled (true);//endtabhost
        this.person_spinner();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_listview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Getroom_status();
//        my_loop();


    }

//    public void my_loop(){
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("my_loop","start");
//
//                Getroom_status();
//                my_loop();
//            }
//
//        }, 5000);
//    }

    public void Getroom_status(){
        Config config = new Config();
        GetService getService = new GetService(getApplicationContext(),config.getSerroom(),null);
        getService.execute();

        try {
            String res = getService.get();

            JSONObject json_room = new JSONObject(res);
            Boolean status_res =  json_room.getBoolean("status");

            if(status_res == true){
                JSONArray room_array = json_room.getJSONArray("data");

                //Toast.makeText(getApplicationContext(), "" + room_array.length(), Toast.LENGTH_LONG).show();
                String[] room_name = new String[room_array.length()];
                String[] room_id = new String[room_array.length()];
                String[] room_status_color = new String[room_array.length()];
                String[] room_status_text = new String[room_array.length()];


                for (int i = 0 ;i< room_array.length() ;i++ ){
                    JSONObject item_obj = room_array.getJSONObject(i);
                    room_name[i] = item_obj.getString("room_name");
                    room_id[i] = item_obj.getString("room_id");
                    room_status_color[i] = item_obj.getString("status_color");
                    room_status_text[i] = item_obj.getString("status_name");
                }

                GridView gridview = (GridView) findViewById(R.id.gridview);
                gridview.setAdapter(new CustomAdapter(this,room_name,room_status_color,room_status_text));

            }





//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                    Toast.makeText(PageMain.this, "" + position, Toast.LENGTH_SHORT).show();
//                }
//            });
            //Toast.makeText(getApplicationContext(),res.toString(),Toast.LENGTH_LONG).show();
        }catch (Exception e){

        }

    }

    public void person_spinner(){
        Spinner spinner = (Spinner) this.findViewById(R.id.editText2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                String display = "You Select: " + adapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String display = "You Select: ";
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // refresh
                refreshMenuItem = item;
                // load the data from server
                new SyncData().execute();
                return true;
            case R.id.action_next:
                ArrayList<Date> selectedDates = (ArrayList<Date>)calendar
                        .getSelectedDates();
                Toast.makeText(PageMain.this, selectedDates.toString(),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_logout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Logout");
                //dialog.setIcon(R.drawable.splash1);
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to logout?");
                dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), MainActivityHome.class);
                        startActivity(i);
                        finish();
                    }
                });

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//    private ArrayList<Date> getHolidays(){
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//        String dateInString = "24-11-2017";
//        Date date = null;
//        try {
//            date = sdf.parse(dateInString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        ArrayList<Date> holidays = new ArrayList<>();
//        holidays.add(date);
//        return holidays;
//    }
    /**
     * Async task to load the data from server
     * **/
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            refreshMenuItem.setActionView(R.layout.action_progressbar);
            refreshMenuItem.expandActionView();
        }

        @Override
        protected String doInBackground(String... params) {
            // not making real request in this demo
            // for now we use a timer to wait for sometime
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
            Getroom_status();
            //show_list_view();  iรอสร้างคลาสคลุม
        }


    }

}
