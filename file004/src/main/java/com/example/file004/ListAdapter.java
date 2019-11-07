package com.example.file004;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private File[] files;

    public ListAdapter(Context context, File[] files) {
        this.context = context;
        this.files = files;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    static  class  ViewHolder{
        public ImageView imageView;
        public TextView textView1;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.line,null);
            holder=new ViewHolder();
            holder.imageView=convertView.findViewById(R.id.icon);
            holder.textView1=convertView.findViewById(R.id.file_name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        for(int i=0;i<2;i++){

        }


        if(files[position].isDirectory()){
            holder.imageView.setImageResource(R.drawable.files);
            holder.textView1.setText(files[position].getName());


        }else {
                holder.imageView.setImageResource(R.drawable.file);
                holder.textView1.setText(files[position].getName());

        }





        //holder.textView1.setText("这是标题。");
        //Glide.with(context).load("https://c-ssl.duitang.com/uploads/item/201604/16/20160416203342_PUsta.jpeg").into(holder.imageView);

        return convertView;

    }


}
