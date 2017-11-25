package com.example.manitch.resortct.grtservice_okhttp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apira on 9/30/2017.
 */

public class GetService extends AsyncTask<String, Void, String> {
    private Context context;
    private String url;
    private String pack = null;



    public GetService(Context context, String url, String pack) {
        this.context = context;
        this.url = url;
        this.pack = pack;
//        this.object = object;
    }


    @Override
    protected String doInBackground(String... params) {

        String json =pack;

        try{

            FormBody.Builder formBody = new FormBody.Builder();
            // check param null
            if(json != null){
                JSONArray jsonParam = new JSONArray(json);
                // loop for
                for (int i = 0 ;i<jsonParam.length();i++){
                    JSONObject jsonObject = new JSONObject(jsonParam.getString(i));

                    //set key
                    final String key = jsonObject.getString("key");

                    //set value
                    final String value = jsonObject.getString("value");

                    formBody.add(key, value);
                }
                // end loop for
            }

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(url).post(formBody.build()).build();
            Response response = okHttpClient.newCall(request).execute();

            if(response.code() == 200){
                //status 200 OK
                String resJson = response.body().string();
                Log.d("OKhttp", "status code ==>" +resJson);
                return resJson;
            }else{
                Log.d("OKhttp", "status code ==>" + response.code());
                return null;
            }
        }catch (Exception e){
            Log.d("OKhttp", "res try ==>" + e.toString());
            return null;
        }

        // if this class error return null

    }



}
