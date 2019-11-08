package com.example.file004;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Video> listvideo;
    private Bitmap bitmap;

    public VideoAdapter(Context context, List<Video> listvideo,Bitmap bitmap) {
        this.context = context;
        this.listvideo=listvideo;
        this.bitmap=bitmap;
        layoutInflater=LayoutInflater.from(context);
    }
    public VideoAdapter(Context context, List<Video> listvideo) {
        this.context = context;
        this.listvideo=listvideo;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listvideo.size();
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
        public ImageView imageView;

        public TextView textView2,textView3;


    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VideoAdapter.ViewHolder holder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.video_list_item,null);
            holder=new VideoAdapter.ViewHolder();
            holder.imageView=view.findViewById(R.id.ig1);
            holder.textView2=view.findViewById(R.id.tv2);
            holder.textView3=view.findViewById(R.id.tv3);
            view.setTag(holder);
        }else{
            holder= (VideoAdapter.ViewHolder) view.getTag();
        }
        bitmap=FileManager.getInstance(context).getVideoThumbnail(listvideo.get(i).getId());
        holder.imageView.setImageBitmap(bitmap);
        holder.textView2.setText(listvideo.get(i).getName());
        holder.textView3.setText(listvideo.get(i).getDuration()+"");
        return view;
    }
}
