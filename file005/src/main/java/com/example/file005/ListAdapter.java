package com.example.file005;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Music> listmusic;

    public ListAdapter(Context context, List<Music> listmusic) {
        this.context = context;
        this.listmusic=listmusic;
        layoutInflater=LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        return listmusic.size();
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

        public TextView textView1,textView2,textView3;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {




        ViewHolder holder=null;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.list_item,null);
            holder=new ViewHolder();
            holder.textView1=convertView.findViewById(R.id.tv1);
            holder.textView2=convertView.findViewById(R.id.tv2);
            holder.textView3=convertView.findViewById(R.id.tv3);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        listmusic.get(position).getPath();
        holder.textView1.setText(position+1+"");
        holder.textView2.setText(listmusic.get(position).getName());
        holder.textView3.setText(listmusic.get(position).getArtist());



        //holder.textView1.setText("这是标题。");
        //Glide.with(context).load("https://c-ssl.duitang.com/uploads/item/201604/16/20160416203342_PUsta.jpeg").into(holder.imageView);

        return convertView;


    }

}
