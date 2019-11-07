package com.example.file004;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {
     ListView listView;
     TextView textView;
     Button button,button1,button2;
     File currentparent;
     File[] currentFiles;
     AlertDialog dialog;
     EditText editText;
     static File watingCopyFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        listView=findViewById(R.id.list);
        textView=findViewById(R.id.path);
        button=findViewById(R.id.parent);
        button1=findViewById(R.id.paste);
        button2=findViewById(R.id.add);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File newFile = new File(currentparent.getPath()+"/"+watingCopyFile.getName());
                if (watingCopyFile.equals(null)) {
                    Toast.makeText(MainActivity.this,"粘贴板为空。",Toast.LENGTH_SHORT).show();

                } else {
                    if (watingCopyFile.isFile()&&watingCopyFile.exists()){
                        try {
                            FileInputStream fis = new FileInputStream(watingCopyFile);
                            FileOutputStream fos = new FileOutputStream(newFile);
                            int len = -1;
                            long contentSize = watingCopyFile.length();
                            long readed = 0;
                            byte[] buff = new byte[8192];
                            while ((len=fis.read(buff))!=-1){
                                //写文件
                                fos.write(buff,0,len);
                                readed+=len;
                                //发布进度
                            }
                            fos.flush();
                            fis.close();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                    if (newFile.exists()) {
                        Toast.makeText(MainActivity.this,"复制" + newFile.getName() + "成功",Toast.LENGTH_SHORT).show();
                        currentFiles=currentparent.listFiles();
                        inflateListView(currentFiles);
                        inflateListView(currentFiles);
                    }
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();;
                dialog.getWindow().setContentView(R.layout.newfloder_dialog);
                dialog.setView(new EditText(MainActivity.this));
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                editText= dialog.getWindow().findViewById(R.id.newfloder_name);
                dialog.getWindow()
                        .findViewById(R.id.newfloder_cancle)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                dialog.getWindow()
                        .findViewById(R.id.newfloder_create)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name =  editText.getText().toString();
                                if (name != null) {
                                    File folder = new File(currentparent.getPath() + "/" + name);
                                    folder.mkdirs();
                                    if (folder.exists()) {
                                        Toast.makeText(MainActivity.this,"文件："+name + " 创建成功",Toast.LENGTH_SHORT).show();
                                        currentFiles=currentparent.listFiles();
                                        inflateListView(currentFiles);
                                        dialog.dismiss();
                                    }
                                }

                            }
                        });

            }
        });

        File root=new File("/mnt/sdcard/");

        if (root.exists()){
            currentparent=root;
            currentFiles=root.listFiles();
            inflateListView(currentFiles);


        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentFiles[i].isFile())
                {
                    CallOtherOpenFile openFile=new CallOtherOpenFile();
                    openFile.openFile(MainActivity.this,currentFiles[i].getAbsolutePath());
                }

                File[] tmp=currentFiles[i].listFiles();
//                if(tmp==null||tmp.length==0){
//                    //Toast.makeText(MainActivity.this,"无文件",Toast.LENGTH_LONG).show();
//                }else {
//                    currentparent=currentFiles[i];
//                    currentFiles=tmp;
//                    inflateListView(currentFiles);
//                }

                if(currentFiles[i].isDirectory()){
                    currentparent=currentFiles[i];
                    currentFiles=tmp;
                    inflateListView(currentFiles);
                }


            }
        });
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Toast.makeText(MainActivity.this,"长按。",Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//        });
        this.registerForContextMenu(listView);

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
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("文件操作：");
        menu.setHeaderIcon(R.drawable.file);
        menu.add(1,1,1,"复制");
        menu.add(1,2,1,"删除");
        menu.add(1,3,1,"重命名");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String id=String.valueOf(info.id);

        switch ((item.getItemId())){
            case 1:
                watingCopyFile=currentFiles[(int) info.id];

                Toast.makeText(MainActivity.this,"已复制。"+id,Toast.LENGTH_SHORT).show();
                break;
            case 2:
                currentFiles[(int) info.id].delete();
                currentFiles=currentparent.listFiles();
                inflateListView(currentFiles);
                Toast.makeText(MainActivity.this,"已删除。",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                dialog = new AlertDialog.Builder(MainActivity.this).create();
                dialog.show();;
                dialog.getWindow().setContentView(R.layout.dialog_rename_file);
                dialog.setView(new EditText(MainActivity.this));
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                editText= dialog.getWindow().findViewById(R.id.et_dlg_rename);
                dialog.getWindow()
                        .findViewById(R.id.btn_dlg_rename_cancle)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                dialog.getWindow()
                        .findViewById(R.id.btn_dlg_rename_confirm)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                String dirName = editText.getText().toString();
                                if (dirName.equals("")) {
                                    Toast.makeText(MainActivity.this,"文件名不能为空。",Toast.LENGTH_SHORT).show();

                                } else {
                                    if (checkSameName(dirName)) {
                                        Toast.makeText(MainActivity.this,"文件名已经存在。",Toast.LENGTH_SHORT).show();

                                    }
                                }

                                //执行修改
                                File file = currentFiles[(int) info.id];
                                file.renameTo(new File(file.getParentFile().getAbsolutePath() + "/" + dirName));
                                currentFiles=currentparent.listFiles();
                                inflateListView(currentFiles);

                            }
                        });

                Toast.makeText(MainActivity.this,"已重命名。",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return super.onContextItemSelected(item);
    }
    private boolean checkSameName(String name) {
        boolean result = false;
        List<String> tempList = new ArrayList<>();
        for (File webFile : currentFiles) {
            if (!webFile.isFile()) {
                tempList.add(webFile.getName());
            }
        }
        if (tempList.contains(name)) {
            result = true;
        }
        return result;
    }


}
