package com.example.kioskserver;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewOrderAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewOrderItem> listViewItemOrderList = new ArrayList<ListViewOrderItem>() ;

    // ListViewAdapter의 생성자
    public ListViewOrderAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemOrderList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_order, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tvOrderNo = (TextView) convertView.findViewById(R.id.item_order_no) ;
        TextView tvOrderTime = (TextView) convertView.findViewById(R.id.item_order_time) ;
        TextView tvOderData = (TextView) convertView.findViewById(R.id.item_order_data) ;

        Button btn = (Button)convertView.findViewById(R.id.item_order_btn);
        btn.setFocusable(false);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewOrderItem listViewOrderItem = listViewItemOrderList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tvOrderNo.setText(listViewOrderItem.getOrderNo());
        tvOrderTime.setText(listViewOrderItem.getOrderTime());
        tvOderData.setText(listViewOrderItem.getOrderDesc());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemOrderList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String num, String time, String desc) {
        ListViewOrderItem item = new ListViewOrderItem();
        String prefix = "주문번호 : ";

        for (ListViewOrderItem listViewOrderItem : listViewItemOrderList) {
            String orderNoPrefix = listViewOrderItem.getOrderNo();
            String orderNo = orderNoPrefix.substring(prefix.length());

            if (orderNo.equals(num))
                return;
        }

        item.setOrderNo(num);
        item.setOrderTime(time);
        item.setOrderDesc(desc);

        listViewItemOrderList.add(item);
    }
}
