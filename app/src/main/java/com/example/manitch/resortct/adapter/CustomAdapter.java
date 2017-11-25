package com.example.manitch.resortct.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manitch.resortct.R;

/**
 * Created by ManitCh on 11/24/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private String[] room_name;
    private String[] status_color;
    private String[] status_name;

    public CustomAdapter(Context mContext, String[] room_name,String[] status_color,String[] status_name) {
        this.mContext = mContext;
        this.room_name = room_name;
        this.status_color =status_color;
        this.status_name =status_name;
    }

    public int getCount() {
        return room_name.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView;
        TextView textView;

//        if (convertView == null) {
            //imageView = new ImageView(mContext);
            textView = new TextView(mContext);



            textView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 250));
//            textView.setPadding(150, 50, 120, 20);
            textView.setGravity(Gravity.CENTER );
            textView.setBackgroundColor(Color.parseColor(status_color[position]));

            // imageView.setScaleType(ImageView.ScaleType.FIT_XY);
           // imageView.setPadding(4, 4, 4, 4);
//        } else {
//            textView = (TextView) convertView;
//           // imageView = (ImageView) convertView;
//        }
        textView.setText(status_name[position]+"\n\n"+room_name[position]);
        //imageView.setImageResource(R.drawable.huhu11);
        return textView;

    }

}