package com.example.file001;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
     ListView listView;
     TextView textView;
     Button button;
     File currentparent;
     File[] currentFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        listView=findViewById(R.id.list);
        textView=findViewById(R.id.path);
        button=findViewById(R.id.parent);

        File root=new File("/mnt/sdcard/");

        if (root.exists()){
            currentparent=root;
            currentFiles=root.listFiles();
            inflateListView(currentFiles);


        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentFiles[i].isFile())return;;

                File[] tmp=currentFiles[i].listFiles();
                if(tmp==null||tmp.length==0){
                    Toast.makeText(MainActivity.this,"无文件",Toast.LENGTH_LONG).show();
                }else {
                    currentparent=currentFiles[i];
                    currentFiles=tmp;
                    inflateListView(currentFiles);
                }


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!currentparent.getCanonicalPath().equals("/mnt/sdcard")){
                        currentparent=currentparent.getParentFile();
                        currentFiles=currentparent.listFiles();
                        inflateListView(currentFiles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void inflateListView(File[] files){


        List<Map<String,Object>> listItems=new ArrayList<>();
        for(int i=0;i<files.length;i++){
            Map<String,Object> listItem=new HashMap<>();
            if(files[i].isDirectory()){
                listItem.put("icon",R.drawable.files);
            }else {
                listItem.put("icon",R.drawable.file);
            }
            listItem.put("filename",files[i].getName());
            listItems.add(listItem);

        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,listItems,R.layout.line,
                new String[]{"icon","filename"},
                new int[]{R.id.icon,R.id.file_name});
        listView.setAdapter(simpleAdapter);
        try {
            textView.setText("当前路径："+currentparent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
