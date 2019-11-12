package com.example.file006;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList name;
    File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


        listView=findViewById(R.id.lv);
        name=new ArrayList();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file=new File("/mnt/sdcard/");
            if(file.exists()){
                files=file.listFiles();
                System.out.println(files.length+"  huieiu");
                getFileName(files);
            }
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,name,R.layout.txt_item,new String[]{"Name"},new int[]{R.id.tv_txt});
        listView.setAdapter(simpleAdapter);

    }


    private void getFileName(final File[] files) {

        if(files!=null){
            Log.i("zeng", "若是文件目录。继续读4" );
            for (File file:files){
                if(file.isDirectory()){
                    getFileName(file.listFiles());
                }else {
                    String filename=file.getName();
                    if(filename.endsWith(".txt")){
                        HashMap map=new HashMap();
                        String s=filename.substring(0,filename.lastIndexOf(".")).toString();
                        map.put("Name",filename.substring(0,filename.lastIndexOf(".")));
                        name.add(map);
                    }
                }
            }
        }

    }
//
//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            getFileName(files);
//
//        }
//    };





}
