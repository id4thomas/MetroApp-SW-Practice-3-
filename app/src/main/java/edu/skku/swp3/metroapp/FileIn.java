package edu.skku.swp3.metroapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by TS on 2018. 5. 12..
 */

public class FileIn extends AsyncTask< Void, Void, Void> {

    private File mFile = null;
    private TextView mTextView;
    private String tag = "FileTask";

    HashMap<Integer,MetroClass> table;
    FileIn(Context context, String filename,HashMap<Integer,MetroClass> tt,TextView t2) {
       table=tt;
       mTextView=t2;

    }


    String res;
    @Override
    protected Void doInBackground(Void... params) {
        Log.i(tag, "background process for file read starts");
        try {
            Scanner sc = new Scanner(mFile);
            StringTokenizer tokenizer;
            int carnum,st,et,intv;
            int label;
            res="";
            String line;
            while (sc.hasNext()) {
                line=sc.nextLine();
                res=res+"\n"+line;
                tokenizer = new StringTokenizer(line, ",");
                label=Integer.parseInt(tokenizer.nextToken());
                carnum=Integer.parseInt(tokenizer.nextToken());
                st=Integer.parseInt(tokenizer.nextToken());
                et=Integer.parseInt(tokenizer.nextToken());
                intv=Integer.parseInt(tokenizer.nextToken());
                if(!table.containsKey(label))//if metro doesn't exist
                {
                    table.put(label,new MetroClass());
                }
                table.get(label).instantiate(carnum,st,et,intv);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mFile = null;
        return null;
    }

    //유아이 처리하는 메인 쓰레드
    @Override
    public void onPostExecute(Void v) {
        //toast message - db load complete
        mTextView.setText(res);
        //mTextView.setText(mTextView.getText().toString() + "\n" + mText);
        mTextView = null;
    }
}
