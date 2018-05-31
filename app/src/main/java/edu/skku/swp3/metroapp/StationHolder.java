package edu.skku.swp3.metroapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by TS on 2018. 5. 31..
 */

public class StationHolder extends AsyncTask< Void, Void, Void> {
    HashMap<String,StationClass> stationmap;
    HashMap<Integer,ArrayList<String>> stationorder;
    File mFile;
    int numlines;
    Context ctxt;
    //ArrayList<String> stations;
    StationHolder(Context context){
        stationmap=new HashMap<>();
        stationorder=new HashMap<>();
        ctxt=context;
        //File parent = context.getDataDir();
       /* mFile = new File(parent,"db.csv");
        if (!mFile.exists()) {
            try {
                if (mFile.createNewFile()) {//파일 생성 시도
                    //Log.e("StationHolder", "File(" + (parent.getAbsolutePath() + filename) + ") already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void addstation(String name,int lane){
        if(stationmap.containsKey(name)) {
            stationmap.get(name).isinterchange=true;
        }else{
            stationmap.put(name,new StationClass(name));
        }
        stationorder.get(lane).add(name);
        return;
    }

    public StationClass getstation(String name){
        return stationmap.get(name);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.i("Reading db", "background process for file read starts");
        Scanner sc = new Scanner(ctxt.getResources().openRawResource(R.raw.db));
        StringTokenizer tokenizer;
        //1. get number of lane infos
        String line,stationname;
        line=sc.nextLine();
        numlines=Integer.parseInt(line);
        ArrayList<String> order;
        int linenum;
        for(int i=0;i<numlines;i++){
            line=sc.nextLine();
            Log.i("Reading First Part", line);
            tokenizer = new StringTokenizer(line, ",");
            order=new ArrayList<>();
            linenum=Integer.parseInt(tokenizer.nextToken());
            stationorder.put(linenum,new ArrayList<String>());
            while(tokenizer.hasMoreTokens()){//get stations
                stationname=tokenizer.nextToken();
                order.add(stationname);//add to order
                addstation(stationname,linenum);
            }
            stationorder.put(linenum,order);
        }//first part complete
        String updown;
        int first,last,intv,numstation;
        MetroClass car;
        for(int i=0;i<numlines;i++){
            line=sc.nextLine();
            Log.i("Reading second part ", line);
            tokenizer = new StringTokenizer(line, ",");
            //up
            linenum=Integer.parseInt(tokenizer.nextToken());
            order=stationorder.get(linenum);
            updown=tokenizer.nextToken();
            first=Integer.parseInt(tokenizer.nextToken());
            last=Integer.parseInt(tokenizer.nextToken());
            intv=Integer.parseInt(tokenizer.nextToken());
            for(int j=first;j<=last;j+=intv){
                car=new MetroClass();
                numstation=order.size();
                for(int k=0;k<numstation;k++) {
                    stationmap.get(order.get(k)).addcar(linenum, updown, j, car);
                }
            }
            //down - reverse order
            line=sc.nextLine();
            tokenizer = new StringTokenizer(line, ",");
            //up
            linenum=Integer.parseInt(tokenizer.nextToken());
            order=stationorder.get(linenum);
            updown=tokenizer.nextToken();
            first=Integer.parseInt(tokenizer.nextToken());
            last=Integer.parseInt(tokenizer.nextToken());
            intv=Integer.parseInt(tokenizer.nextToken());
            for(int j=first;j<=last;j+=intv){
                car=new MetroClass();
                numstation=order.size();
                for(int k=numstation-1;k>0;k--) {
                    stationmap.get(order.get(k)).addcar(linenum, updown, j, car);
                }
            }
        }//completed stations
        //get paths
        Log.i("Finished Reading db", "Read complete");
        sc.close();
        //mFile = null;
        return null;
    }

    //유아이 처리하는 메인 쓰레드
    @Override
    public void onPostExecute(Void v) {
        //toast message - db load complete
       // mTextView.setText(res);
        //mTextView.setText(mTextView.getText().toString() + "\n" + mText);
       // mTextView = null;
    }


}
