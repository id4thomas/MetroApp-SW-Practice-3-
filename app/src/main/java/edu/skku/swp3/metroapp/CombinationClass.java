package edu.skku.swp3.metroapp;

import java.util.HashMap;

/**
 * Created by TS on 2018. 5. 12..
 */

public class CombinationClass {
    StationClass s1,s2;
    HashMap<String,StationClass> metromap;
    CombinationClass(StationClass t1,StationClass t2,HashMap<String,StationClass> mm){
        s1=t1;
        s2=t2;
        metromap=mm;
    }
/*
    public void answer(int currenttime){
        boolean samelane=false;
        int lane1=0,lane2=0;
        for(Integer i : s1.stationtable.keySet()){
            if(s2.stationtable.containsKey(i)){
                samelane=true;
                lane1=i;
                break;
            }
        }
        int totaltime=0;
        int traintime=0;
        if(samelane){//on same lane
            totaltime=s1.stationtable.get(lane1).closest(currenttime,false);
            getdistance(s1.stationname,s2.stationname);
        }
        else
        {
            for(Integer i : s1.stationtable.keySet()){
                lane1=i;
            }
            for(Integer i : s2.stationtable.keySet()){
                lane2=i;
            }
            totaltime=s1.stationtable.get(lane1).closest(currenttime,false);
            if(s1.stationname=="종신대")
            {
                totaltime=s1.stationtable.get(3).closest(currenttime,false);
            }
            else if(s1.stationname=="사당"){
                totaltime=s1.stationtable.get(3).closest(currenttime,false);
            }
            if(lane1==1&&lane2==2){//1->2: 1->3,3->2
                getdistance(s1.stationname,"종신대");
                traintime=metromap.get("종신대").stationtable.get(3).closest(totaltime,false);
                getdistance("종신대","사당");
                traintime=metromap.get("사당").stationtable.get(2).closest(totaltime,false);
                getdistance("사당",s2.stationname);

            }
            else if(lane2==1&&lane1==2){//2->1: 2->3 3->1

                getdistance(s1.stationname,"사당");
                getdistance("사당","종신대");
                getdistance("종신대",s2.stationname);
            }
            else
            {
                if(lane1==3){
                    if(lane2==1){
                        //"총신대"
                    }
                    else
                    {
                        //"사당"
                    }
                }
                else if(lane1==1){
                    //"총신대"
                }
                else if(lane1==2){
                    //"사당"
                }
            }
        }
    }
    public int getdistance(String source,String dest){
        StationClass s;
        s=metromap.get(source);
        return s.samelane.get(dest);

    }*/


}
