package com.example.filesystem;

import android.Manifest;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class FileActivity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    Button button,button1,button2;
    File currentparent;
    File[] currentFiles;
    AlertDialog dialog;
    EditText editText;
    boolean canback=true;
    static File watingCopyFile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        listView=findViewById(R.id.list);
        textView=findViewById(R.id.path);
        button=findViewById(R.id.parent);
        button1=findViewById(R.id.paste);
        button2=findViewById(R.id.add);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (watingCopyFile==null) {
                    Toast.makeText(FileActivity.this,"粘贴板为空。",Toast.LENGTH_SHORT).show();

                } else {
                    File newFile = new File(currentparent.getPath()+"/"+watingCopyFile.getName());
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
                        Toast.makeText(FileActivity.this,"复制" + newFile.getName() + "成功",Toast.LENGTH_SHORT).show();
                        currentFiles=currentparent.listFiles();
                        currentFiles=Sort(currentFiles);
                        inflateListView(currentFiles);

                    }
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(FileActivity.this).create();
                dialog.show();;
                dialog.getWindow().setContentView(R.layout.newfloder_dialog);
                dialog.setView(new EditText(FileActivity.this));
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
                                File folder = new File(currentparent.getPath() + "/" + name);
                                if(name!=null&&folder.exists()){
                                    Toast.makeText(FileActivity.this,"文件夹已存在。",Toast.LENGTH_SHORT).show();

                                }else if (name != null) {
                                    folder.mkdirs();
                                    if (folder.exists()) {
                                        Toast.makeText(FileActivity.this,"文件夹创建成功",Toast.LENGTH_SHORT).show();
                                        currentFiles=currentparent.listFiles();
                                        currentFiles=Sort(currentFiles);
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
            currentFiles=Sort(currentFiles);
            inflateListView(currentFiles);


        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentFiles[i].isFile())
                {
                    CallOtherOpenFile openFile=new CallOtherOpenFile();
                    openFile.openFile(FileActivity.this,currentFiles[i].getAbsolutePath());
                }

                File[] tmp=currentFiles[i].listFiles();

                if(currentFiles[i].isDirectory()){
                    currentparent=currentFiles[i];
                    currentFiles=tmp;
                    currentFiles=Sort(currentFiles);
                    inflateListView(currentFiles);
                }


            }
        });
        this.registerForContextMenu(listView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                back();

            }
        });

    }
    private void inflateListView(File[] files){
        ListAdapter listAdapter=new ListAdapter(FileActivity.this,files);
        listView.setAdapter(listAdapter);

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

                Toast.makeText(FileActivity.this,"已复制。"+id,Toast.LENGTH_SHORT).show();
                break;
            case 2:

                FileProcessing fileProcessing=new FileProcessing();
                fileProcessing.delete(currentFiles[(int) info.id]);


//                if(currentFiles[(int) info.id].isDirectory()){
//                    deleteDirectory(currentFiles[(int) info.id].getPath());
//                }else {
//                    currentFiles[(int) info.id].delete();
//                }
                currentFiles=currentparent.listFiles();
                currentFiles=Sort(currentFiles);
                inflateListView(currentFiles);
                Toast.makeText(FileActivity.this,"已删除。",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                dialog = new AlertDialog.Builder(FileActivity.this).create();
                dialog.show();;
                dialog.getWindow().setContentView(R.layout.dialog_rename_file);
                dialog.setView(new EditText(FileActivity.this));
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
                                    Toast.makeText(FileActivity.this,"文件名不能为空。",Toast.LENGTH_SHORT).show();

                                } else {
                                    if (checkSameName(dirName)) {
                                        Toast.makeText(FileActivity.this,"文件名已经存在。",Toast.LENGTH_SHORT).show();

                                    }
                                }

                                //执行修改
                                File file = currentFiles[(int) info.id];
                                file.renameTo(new File(file.getParentFile().getAbsolutePath() + "/" + dirName));
                                currentFiles=currentparent.listFiles();
                                currentFiles=Sort(currentFiles);
                                inflateListView(currentFiles);

                            }
                        });
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


    public File[] Sort(File[] files){
        File[] filesort=new File[files.length];
        int s=0;

        for (int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                filesort[s]=files[i];
                s++;
            }
        }
        for (int i=0;i<files.length;i++){
            if(!files[i].isDirectory()){
                filesort[s]=files[i];
                s++;
            }
        }
        files=filesort;
        return files;

    }
    public void back(){
        try {
            if(!currentparent.getCanonicalPath().equals("/storage/emulated/0")){
                currentparent=currentparent.getParentFile();
                currentFiles=currentparent.listFiles();
                currentFiles=Sort(currentFiles);
                inflateListView(currentFiles);

            }else {
                canback=false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&canback){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    public void deleteDirectory(String filePath) {

        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return;
        }
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();
            } else {
                deleteDirectory(files[i].getAbsolutePath());
            }
        }
        dirFile.delete();
    }

}