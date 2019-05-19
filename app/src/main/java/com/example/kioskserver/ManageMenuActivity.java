package com.example.kioskserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ManageMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMenuActivity.this, ManageMenuSettingActivity.class));
            }
        });


        ListView listview ;
        ListViewMenuAdapter adapter;

        // Adapter 생성
        adapter = new ListViewMenuAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem("메뉴이름1", "Hot Only", "* S: 5000원 | M: 6000원 | L: 7000원") ;
        adapter.addItem("메뉴이름2", "Ice Only", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;
        adapter.addItem("메뉴이름3", "HOT or ICE", "* 10000원") ;


    }
}
