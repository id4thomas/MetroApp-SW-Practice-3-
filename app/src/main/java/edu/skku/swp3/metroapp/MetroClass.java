package edu.skku.swp3.metroapp;

/**
 * Created by TS on 2018. 5. 11..
 */

public class MetroClass {

    int interval;//배차간격
    int starttime;//첫차
    int endtime;//막차

    int cars;//몇칸 있는지

    int schedule;
    //time calculation : hour*60 + minute
    public void instantiate(int carnum,int st,int et, int intv){
        cars=carnum;
        starttime=st;
        endtime=et;
        interval=intv;
        return;
    }

    public int closest(int target,boolean next){
        int t,calculated;
        t=(target-starttime)/interval+1;
        calculated=starttime + interval * t;
        if(next)//get the next available train
        {
            calculated=calculated+interval;
        }
        if(calculated>endtime)
        {
            return -1;//there is no train available
        }
        else
        {
            return calculated;
        }
    }
    public int passengercount(int carnum){
        int x=0;

        //get from artik

        return x;
    }


}
