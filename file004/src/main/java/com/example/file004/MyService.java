package com.example.file004;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MyService extends Service {
    private Context context;

    private ListView listView;
    ArrayList name;
    public MyService(Context context) {
        this.context=context;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                name=new ArrayList();
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    File file=new File("/mnt/sdcard/");
                    if(file.exists()){
                        File[] files=file.listFiles();
                        System.out.println(files.length+"  hhhhhh");
                        getFileName(files);
                    }
                }

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
    private void getFileName(File[] files) {
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
}
