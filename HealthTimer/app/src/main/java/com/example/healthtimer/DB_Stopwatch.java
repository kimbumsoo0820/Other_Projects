package com.example.healthtimer;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class DB_Stopwatch extends AppCompatActivity {
    Handler timerHandler = null;
    //멤버변수화
    private TextView timer;
    private ScrollView scrollView;
    private TextView record;
    private Button bt_sta,bt_rec;
    private Handler handler1 = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            txt_rest.setText("휴식"+rest_check+"초");
            rest_check--;

            if (rest_check >= 0) {
                handler1.postDelayed(runnable,1000);
                bt_rec.setEnabled(false);
            }else{
                handler1.removeCallbacks(runnable);
                bt_rec.setEnabled(true);
            }
        }
    };

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = firebaseDatabase.getReference();



    long m ;
    long s ;
    long ms;



    String recTime,time_data;
    TextView txt_rest,txt_set;
    String event;
    Integer set, rest,rest_check;

    Timer timer_2 = new Timer();
    Handler mHandler = new Handler();

    //상태를 표시하는 '상수' 지정
    //- 각각의 숫자는 독립적인 개별 '상태' 의미
    public static final int INIT = 0;//처음
    public static final int RUN = 1;//실행중
    public static final int PAUSE = 2;//정지

    //상태값을 저장하는 변수
    //- INIT은 초기값임, 그걸 status 안에 넣는다.(0을 넣은거다)
    public static int status = INIT;

    //기록할때 순서 체크를 위한 변수
    private int cnt = 1;

    //타이머 시간 값을 저장할 변수
    private long baseTime,pauseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_stopwatch);


        //객체화
        timer = (TextView)findViewById(R.id.timer);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        record = (TextView)findViewById(R.id.record);
        bt_sta = (Button)findViewById(R.id.bt_sta);
        bt_rec = (Button)findViewById(R.id.bt_rec);
        bt_rec.setEnabled(false);//비활성화

        bt_sta.setOnClickListener(onClickListener);
        bt_rec.setOnClickListener(onClickListener);


        TextView txt_event = (TextView)findViewById(R.id.txt_event);
        txt_set = (TextView)findViewById(R.id.txt_set);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

       event = intent.getExtras().getString("이벤트");
       set = intent.getExtras().getInt("세트");
       rest = intent.getExtras().getInt("휴식");
       txt_event.setText("종목 :" + event);
       txt_set.setText("남은 세트수 : "+set);





    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            txt_rest=(TextView)findViewById(R.id.txt_rest);
            switch (view.getId()){
                case R.id.bt_sta:
                    staButton();
                    bt_rec.setEnabled(true);
                    handler1.removeCallbacks(runnable);
                    if(rest==1){rest_check=60;}
                    else if(rest==2){rest_check=120;}
                    else if(rest==3){rest_check=180;}
                    break;

                case R.id.bt_rec:
                    recButton();
                    set--;
                    if(rest==1){
                        rest_check=60;
                        handler.postDelayed(runnable, 0);
                    }
                    else if(rest==2) {
                        rest_check=120;
                        handler.postDelayed(runnable, 0);
                    }
                    else if(rest==3) {
                        rest_check=180;
                        handler.postDelayed(runnable, 0);
                    }

                    txt_set.setText("총 세트수 : "+set);

                    if(set<=0){

                        time_data = String.format("%02d:%02d:%03d",m,s,ms);



                        DatabaseReference dataRef = rootRef.child(event); //
                        dataRef.push().setValue(time_data);

                        DatabaseReference memberRef = rootRef.child("events");
                        DatabaseReference itemRef = memberRef.push();
                        itemRef.child(event).setValue(time_data);

                        memberRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                StringBuffer buffer = new StringBuffer();
                                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {

                                        buffer.append(ds.getKey() + " : " + ds.getValue() + "\n");

                                        buffer.append("\n");
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        StartToast(event+"");

                        finish();

                    }

            }


        }
    };


//질문: 왜 위에서 INIT(초기값)을 넣은 status 를 스위치문에서 다시 캐스팅 하는가?
    //대답: 그래야 상태값을 확인할수 있기 때문이다. 즉, 달라지는 상태값을 알기위해선 기준이 되는 초기값이 필요한것!

    private void staButton(){

            switch (status) {
                case INIT:
                    //어플리케이션이 실행되고 나서 실제로 경과된 시간...
                    baseTime = SystemClock.elapsedRealtime();

                    //핸들러 실행
                    handler.sendEmptyMessage(0);
                    bt_sta.setText("멈춤");
                    bt_rec.setEnabled(true);

                    //상태 변환
                    status = RUN;
                    break;
                case RUN:
                    //핸들러 정지
                    handler.removeMessages(0);

                    //정지 시간 체크
                    pauseTime = SystemClock.elapsedRealtime();

                    bt_sta.setText("시작");
                    bt_rec.setText("리셋");

                    //상태변환
                    status = PAUSE;
                    break;
                case PAUSE:
                    long reStart = SystemClock.elapsedRealtime();
                    baseTime += (reStart - pauseTime);

                    handler.sendEmptyMessage(0);

                    bt_sta.setText("멈춤");
                    bt_rec.setText("휴식");

                    status = RUN;


        }

    }


    private void recButton(){

            switch (status){
                case RUN:
                    String timeList = record.getText().toString();
                    timeList+= String.format("%2d. %s\n",cnt,getTime());

                    record.setText(timeList);

                    cnt++;

                    break;
                case PAUSE:
                    bt_sta.setText("시작");
                    bt_rec.setText("휴식");
                    bt_rec.setEnabled(false);

                    timer.setText("00:00:00");
                    record.setText("");

                    baseTime = 0;
                    pauseTime = 0;

                    cnt = 1;

                    status = INIT;




        }

    }






    private String getTime(){
        //경과된 시간 체크

        long nowTime = SystemClock.elapsedRealtime();
        //시스템이 부팅된 이후의 시간?
        long overTime = nowTime - baseTime;

         m = overTime/1000/60;
         s = (overTime/1000)%60;
         ms = overTime % 1000;

        recTime = String.format("%02d:%02d:%03d",m,s,ms);


        return recTime;
    }





    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {

            timer.setText(getTime());

            //
            handler.sendEmptyMessage(0);
        }
    };
    private void StartToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}