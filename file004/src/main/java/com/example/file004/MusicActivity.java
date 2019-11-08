package com.example.file004;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        listView=findViewById(R.id.listview);
        final List<Music> list=FileManager.getInstance(MusicActivity.this).getMusics();
        MusicAdapter musicAdapter=new MusicAdapter(MusicActivity.this,list);
        listView.setAdapter(musicAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CallOtherOpenFile openFile=new CallOtherOpenFile();
                openFile.openFile(MusicActivity.this,list.get(i).getPath());
            }
        });

    }


}
