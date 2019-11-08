package com.example.file004;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ImgFolderBean> imgFolderBeans;

    public PhotoAdapter(Context context, List<ImgFolderBean> imgFolderBeans) {
        this.context = context;
        this.imgFolderBeans=imgFolderBeans;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return imgFolderBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    static  class  ViewHolder{

        public TextView textView2,textView3;


    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

MusicAdapter.ViewHolder holder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.list_item,null);
            holder=new MusicAdapter.ViewHolder();
            holder.textView1=view.findViewById(R.id.tv1);
            holder.textView2=view.findViewById(R.id.tv2);
            holder.textView3=view.findViewById(R.id.tv3);
            view.setTag(holder);
        }else{
            holder= (MusicAdapter.ViewHolder) view.getTag();
        }

        holder.textView2.setText(imgFolderBeans.get(i).getName());
        holder.textView3.setText(imgFolderBeans.get(i).getFistImgPath());

        //holder.textView1.setText("这是标题。");
        //Glide.with(context).load("https://c-ssl.duitang.com/uploads/item/201604/16/20160416203342_PUsta.jpeg").into(holder.imageView);

        return view;
    }
}
