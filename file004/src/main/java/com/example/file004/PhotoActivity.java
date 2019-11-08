package com.example.file004;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        listView=findViewById(R.id.listview);

        List<ImgFolderBean> list=FileManager.getInstance(PhotoActivity.this).getImageFolders();
        PhotoAdapter photoAdapter=new PhotoAdapter(PhotoActivity.this,list);
        listView.setAdapter(photoAdapter);




    }
}
