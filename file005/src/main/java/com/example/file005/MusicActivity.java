package com.example.file005;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        listView=findViewById(R.id.listview);
        List<Music> list=FileManager.getInstance(MusicActivity.this).getMusics();
        ListAdapter listAdapter=new ListAdapter(MusicActivity.this,list);
        listView.setAdapter(listAdapter);




    }

}
