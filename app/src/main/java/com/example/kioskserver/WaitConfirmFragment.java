package com.example.kioskserver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class WaitConfirmFragment extends Fragment {

    public WaitConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_confirm, null);

        ListView listview ;
        ListViewOrderAdapter adapter;

        // Adapter 생성
        adapter = new ListViewOrderAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView)view.findViewById(R.id.listview_wait_confirm);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem("1", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("2", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("3", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("4", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("5", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("6", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("7", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("8", "10:17", "아이스아메리카노 외 1개") ;
        adapter.addItem("9", "10:17", "아이스아메리카노 외 1개") ;

        return view;
    }

}
