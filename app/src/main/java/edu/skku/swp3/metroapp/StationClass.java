package edu.skku.swp3.metroapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by TS on 2018. 5. 22..
 */
/*
public class StationClass {
    HashMap<Integer,MetroClass> stationtable;
    HashMap<String,Integer> samelane;
    String stationname;
    boolean interchange;
    //String[] intcinfo;
    HashMap<Integer,String> intcinfo;
    StationClass(String sn,boolean intc){
        stationname=sn;
        interchange=intc;
        stationtable=new HashMap<Integer,MetroClass>();
        intcinfo=new HashMap<Integer,String>();
        samelane=new HashMap<String,Integer>();
        samelane.put(stationname,0);
    }

    void addline(int linenum,int carnum,int starttime,int endtime,int interval){
        MetroClass line=new MetroClass();
        line.instantiate(carnum,starttime,endtime,interval);
        stationtable.put(linenum,line);
    }

    void addadj(String destination,int distance)
    {
        samelane.put(destination,distance);
    }

    void addintc(int destinationline,String destinationstation)
    {
        intcinfo.put(destinationline,destinationstation);
    }
}*/
public class StationClass {
    HashMap<ArrayList<String>,MetroClass> stationtable;
    String stationname;
    public boolean isinterchange;

    StationClass(String name){

        stationtable= new HashMap<>();
        stationname=name;
        isinterchange=false;
    }

    public void addcar(int lane,String updown, int time,MetroClass car){
        ArrayList<String> key=new ArrayList<>();
        key.add(Integer.toString(lane));//lane info
        key.add(updown);//up,down info
        key.add(Integer.toString(time));//car time
        stationtable.put(key,car);
        return;
    }

    public MetroClass getcar(int lane,String updown, int time){
        ArrayList<String> key=new ArrayList<>();
        key.add(Integer.toString(lane));//lane info
        key.add(updown);//up,down info
        key.add(Integer.toString(time));//car time
        return stationtable.get(key);
    }

}