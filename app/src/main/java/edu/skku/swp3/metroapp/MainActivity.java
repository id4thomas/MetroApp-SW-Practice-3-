package edu.skku.swp3.metroapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private Button btnList; //출발시간 선택 버튼
    private Button departList; //출발지 선택 버튼
    private Button arrivalList; //도착지 선택 버튼
    private TextView departText;    //출발 시간 텍스트뷰 선택시 내용 바뀜
    private String departure; //출발지
    private String arrival;     //도착지
    private String departTime;  //출발 시간
    private int select_depart=0; //출발지 선택 햇는지 유무
    private int select_arrive=0; //도착지 선택 했는지 유무

    //5/17 test
    private Button testbutton;//그냥 아웃풋 테스트용
    private TextView testoutput;//"
    HashMap<Integer,MetroClass> timetable;
    private CombinationClass c1;
    private TextView testfile;
    private Button readfile;
    private Button writefile;
    private View.OnClickListener readlistener = new View.OnClickListener(){

        public void onClick(View v)
        {
            c1=new CombinationClass();
            FileIn fileTask = new FileIn(getApplicationContext(), "schedule.csv",timetable,testfile);
            fileTask.execute();
        }
    };

    private View.OnClickListener writelistener = new View.OnClickListener(){

        public void onClick(View v)
        {
            String[] wow = new String[1];
            FileOut fileTask = new FileOut(getApplicationContext(), "schedule.csv");
            ArrayList<String> strings = new ArrayList<>(); //1줄당 어레이리스트 아이템 한개i
            String data_example="1,2,300,1200,30\n,2,2,330,1230,20";

            //data_example=al.getText().toString();
            StringTokenizer tokenizer = new StringTokenizer(data_example, "\n");
            while (tokenizer.hasMoreTokens()) {
                strings.add(tokenizer.nextToken());
            }
            fileTask.execute(strings.toArray(wow));//리턴해야할 데이터 타입이 스트링 배열이라고선언 안하면 오브젝트 배열로 넘어감
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> selectedItems = new ArrayList<String>();

        departText=(TextView)findViewById(R.id.depart_text);
        departList=(Button)findViewById(R.id.depart_btn);
        arrivalList=(Button)findViewById(R.id.arrival_btn);
        btnList = (Button) findViewById(R.id.btnList);

        testbutton=findViewById(R.id.testbutton);
        testoutput=findViewById(R.id.test_text);
        testfile=findViewById(R.id.test_input);
        readfile=findViewById(R.id.testread);
        writefile=findViewById(R.id.testwrite);
        readfile.setOnClickListener(readlistener);
        writefile.setOnClickListener(writelistener);
        timetable= new HashMap<Integer,MetroClass>();
        testbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MetroClass f,t;
                f=timetable.get(1);
                t=timetable.get(2);
                c1=new CombinationClass();
                c1.instantiate(f,t);
                Calendar rightNow = Calendar.getInstance();
                int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
                int currentMinute= rightNow.get(Calendar.MINUTE);
                int x=t.closest(60*currentHour+currentMinute,false);//암사방편
                String info;
                info="You should get on "+String.valueOf(x/60)+":"+String.valueOf(x%60)+" train";
                testoutput.setText(info);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"오전 11시", "오후 12시", "오후 1시"};
                final int[] selectedIndex = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog .setTitle("출발 시간")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { selectedIndex[0] = which; } })
                                .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    departTime=items[selectedIndex[0]];
                                    departText.setText(items[selectedIndex[0]]);

                                }
                            }).create().show();
            }
        });

        departList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"성균관대역", "수원역"};
                final int[] selectedIndex = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog .setTitle("출발역")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { selectedIndex[0] = which; } })
                        .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(select_arrive!=0) {
                                    if (arrival.equals(items[selectedIndex[0]])) {
                                        select_depart=1;
                                        Toast.makeText(MainActivity.this, "출발지와 도착지가 같습니다. \n다시 선택해주세요!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        departList.setText(items[selectedIndex[0]]);
                                        departure = items[selectedIndex[0]];
                                    }
                                }
                                else{
                                    departList.setText(items[selectedIndex[0]]);
                                    departure = items[selectedIndex[0]];
                                    select_depart=1;
                                }
                            }

                        }).create().show();
            }
        });
        arrivalList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"성균관대역", "수원역"};
                final int[] selectedIndex = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog .setTitle("도착역")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { selectedIndex[0] = which; } })
                        .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(select_depart!=0) {
                                    if (departure.equals(items[selectedIndex[0]])) {
                                        select_arrive=1;
                                        Toast.makeText(MainActivity.this, "출발지와 도착지가 같습니다. \n다시 선택해주세요!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        arrivalList.setText(items[selectedIndex[0]]);
                                        arrival = items[selectedIndex[0]];
                                    }
                                }
                                else{
                                    arrivalList.setText(items[selectedIndex[0]]);
                                    arrival = items[selectedIndex[0]];
                                    select_arrive=1;
                                }
                            }
                        }).create().show();
            }
        });
    }
}

