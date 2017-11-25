package com.example.manitch.resortct.main_splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.manitch.resortct.R;
import com.example.manitch.resortct.config.Config;
import com.example.manitch.resortct.grtservice_okhttp.GetService;
import com.example.manitch.resortct.main_home.MainActivityHome;
import com.example.manitch.resortct.main_page.PageMain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;



public class SplashScreen extends Activity {
//    Handler handler;
//    Runnable runnable;
//    long delay_time;
//    long time = 2000L;
    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;
    private SharedPreferences sharedPreferences;
    TextView textView;
    private Boolean status_login =false;
    private String m_user,m_pass;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        textView=(TextView)findViewById(R.id.textView);
        textView.setText("");

        // shereoreference
        sharedPreferences = getApplicationContext().getSharedPreferences("session_member", Context.MODE_PRIVATE);
        status_login = sharedPreferences.getBoolean("status_login",false);

        final long period = 30;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i)+"%");
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                }else{
                    //closing the timer
                    timer.cancel();
//                    if (status_login==true){
////                        SharedPreferences.Editor editor = sharedPreferences.edit();
////
////                        editor.clear().commit();
//                        m_user = sharedPreferences.getString("m_user","");
//                        m_pass = sharedPreferences.getString("m_pass","");
//                        checkLogin(m_user,m_pass);
//
//
//                    }else {
                        //Toast.makeText(getApplicationContext(),"Not....login",Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(SplashScreen.this,MainActivityHome.class);
                        startActivity(intent);
                        // close this activity
                        finish();
//                    }


                }
            }
        }, 0, period);
    }
    public void checkLogin(String username,String password) {
            Config config = new Config();

            String body = "[{'key':'user','value':'"+username+"'},{'key':'pass','value':'"+password+"'}]";


            GetService getService = new GetService(getApplicationContext(),config.getSerlogin(),body);
            getService.execute();
        try {
            String res = getService.get();
            Log.d("444",res);
            if(res!=null){

                JSONObject jsonObject = new JSONObject(res.toString());
                boolean login_status = jsonObject.getBoolean("status");
                String data_obj = jsonObject.getString("data");
                JSONArray jsonArray_data = new JSONArray(data_obj);

                if (login_status==true) {
                    JSONObject data_user = jsonArray_data.getJSONObject(0);
                    // intent to home page list
                    Intent intent = new Intent(getApplicationContext(),PageMain.class);
                    intent.putExtra("data",data_user.toString());
                    startActivity(intent);

                    SplashScreen.this.finish();
                }else {
                    Intent intent =new Intent(SplashScreen.this,MainActivityHome.class);
                    startActivity(intent);
                    // close this activity
                    SplashScreen.this.finish();
                }
            }else{
                 Log.d("home","null");
            }

        }catch (Exception e){
            Log.d("home",e.toString());
            //Toast.makeText(this, "ไม่สามารถเชื่อต่อได้", Toast.LENGTH_SHORT).show();
        }

    }
}