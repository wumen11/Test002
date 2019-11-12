package com.example.file004;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private ListView listView;
    private Video video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video=new Video();
        listView=findViewById(R.id.listview);
        final List<Video> list=FileManager.getInstance(VideoActivity.this).getVideos();
        VideoAdapter videoAdapter=new VideoAdapter(VideoActivity.this,list);
        listView.setAdapter(videoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CallOtherOpenFile openFile=new CallOtherOpenFile();
                openFile.openFile(VideoActivity.this,list.get(i).getPath());
            }
        });



    }


}
