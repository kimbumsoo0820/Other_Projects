package com.example.healthtimer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import adapter.EventAdapter;

public class Add_event_main extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private ArrayList<Add_event> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // 이벤트 객체를 담을 어레이 리스트 (어댑터 쪽으로)
        TextView tv_add = (TextView)findViewById(R.id.tv_add);

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Add_event_main.this, Add_events_test.class);
                startActivity(intent);

            }
        });


        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("Add_events"); // db 테이블 연결  -> 파베 부분에 데이터베이스 이름
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열라스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //  반복문으로 데이터 LIst를 추출해냄
                    Add_event add_event = snapshot.getValue(Add_event.class); // 만들어뒀던 Add_evet 객체에 데이터를 넣는다
                    arrayList.add(add_event); // 담은 데이터들을 배열 리스트에 넣고 리사이클러뷰로 보낼 준비

                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseerror) {
                // 디비를 가져오던 중 에러 발생시
                Log.e("Add_evnet_main", String.valueOf(databaseerror.toException())); // 에러를 출력
            }
        });
        adapter = new EventAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결


    }


}



