package com.example.filesystem;

import android.content.Context;

import java.io.File;

public class FileProcessing {

    Context context;
    File file;

    public FileProcessing(Context context, File file) {
        this.context = context;
        this.file = file;
    }
    public  void delete(File file){

        if(file.isDirectory()){
            deleteDirectory(file.getPath());
        }else {
            file.delete();
        }


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

    public void copy(){

    }







}
