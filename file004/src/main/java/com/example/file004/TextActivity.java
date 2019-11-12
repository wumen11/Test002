package com.example.file004;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        listView=findViewById(R.id.listview);
        name=new ArrayList();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file=new File("/mnt/sdcard/");
            if(file.exists()){
                File[] files=file.listFiles();
                System.out.println(files.length+"  hhhhhh");
                //getFileName(files);
            }
        }





    }
}
