package edu.skku.swp3.metroapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by TS on 2018. 5. 12..
 */

public class FileOut extends AsyncTask<String, Void, Void> { //무거워서 async로 돌려버
    private File mFile = null;
    private String tag = "FileTask";

    FileOut(Context context, String filename) {
        File parent = context.getDataDir();
        mFile = new File(parent, filename);
        if(mFile.exists())
        {
            mFile.delete();
        }
        if (!mFile.exists()) {
            try {
                if (mFile.createNewFile()) {
                    Log.e(tag, "File(" + (parent.getAbsolutePath() + filename) + ") already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            FileOutputStream fos=new FileOutputStream(mFile,true);
            PrintWriter pw = new PrintWriter(fos);
            for (String string : strings) {
                pw.append(string).append("\n");
            }
            pw.flush();//실제 파일에 작성이됨
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mFile = null; //
        return null;
    }
}
