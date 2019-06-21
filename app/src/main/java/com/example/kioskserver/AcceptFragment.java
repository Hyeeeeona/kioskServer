package com.example.kioskserver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcceptFragment extends Fragment {
    private NetworkService networkService;
    String desc="";
    ListView listview ;
    ListViewOrderAdapter adapter;
    int selectedPos = -1;
    List<OrderInfo> orderInfoList;

    public AcceptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accept, null);


        // Adapter 생성
        adapter = new ListViewOrderAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView)view.findViewById(R.id.listview_accept);
        listview.setAdapter(adapter);


            Button btn = (Button) view.findViewById(R.id.item_order_btn);

            listview.setFocusable(false);

/*        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hyeona", "버튼크릵!");
            }
        });*/
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String prefix = "주문번호 : ";
                    String orderNoPrefix = ((ListViewOrderItem) adapter.getItem(position)).getOrderNo();
                    String orderNo = orderNoPrefix.substring(prefix.length());
                    final String desc;
                    selectedPos = position;

                    Call<List<OrderDetail>> getOrderDetailCall = networkService.get_pk_orderdetail(Integer.parseInt(orderNo));
                    getOrderDetailCall.enqueue(new Callback<List<OrderDetail>>() {
                        @Override
                        public void onFailure(Call<List<OrderDetail>> call, Throwable t) {
                        }

                        @Override
                        public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                            List<OrderDetail> orderDetailList = response.body();
                            String desc = "";
                            for (OrderDetail orderDetail : orderDetailList) {
                                desc += orderDetail.getMenu_name();
                                desc += "/";
                                desc += orderDetail.getMenu_size();
                                desc += "/";
                                desc += orderDetail.getQuantity();
                                desc += "/";
                                desc += orderDetail.getTotal();
                                desc += '\n';
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("상세내용");
                            builder.setMessage(desc);
                            builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String selectedItemOrderNoPrefix = ((ListViewOrderItem) adapter.getItem(selectedPos)).getOrderNo();
                                            String prefix = "주문번호 : ";
                                            String selectedItemOrderNo = selectedItemOrderNoPrefix.substring(prefix.length());

                                            for (OrderInfo orderInfo : orderInfoList) {
                                                if (orderInfo.getOrder_id() == Integer.parseInt(selectedItemOrderNo)) {
                                                    orderInfo.setStatus(2);

                                                    Call<OrderInfo> getCall = networkService.patch_orderinfo(orderInfo.getOrder_id(), orderInfo);

                                                    getCall.enqueue(new Callback<OrderInfo>() {
                                                        @Override
                                                        public void onFailure(Call<OrderInfo> call, Throwable t) {
                                                            Log.d("debugging", "Fail Message : " + t.getMessage());
                                                        }

                                                        @Override
                                                        public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                                                            if (response.isSuccessful()) {
                                                                Toast.makeText(getContext(), "준비 완료!", Toast.LENGTH_SHORT).show();
                                                            }

                                                            reload();
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });


                            builder.setNegativeButton("뒤로가기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            });


        networkService = ApplicationController.getInstance().getNetworkService();

        final int shop_id = ShopInfoDataFileIO.getShopId(getContext());
        Log.d("hyeona", "shop_id : " + shop_id);
        if (shop_id != -1) {
            Call<List<OrderInfo>> getCall = networkService.get_orderinfo();

            getCall.enqueue(new Callback<List<OrderInfo>>() {
                @Override
                public void onFailure(Call<List<OrderInfo>> call, Throwable t) {
                    Log.d("debugging", "Fail Message : " + t.getMessage());
                }

                @Override
                public void onResponse(Call<List<OrderInfo>> call, Response<List<OrderInfo>> response) {
                    if (response.isSuccessful()) {
                        orderInfoList = response.body();

                        for (final OrderInfo orderInfo : orderInfoList) {
                            int orderShopId = orderInfo.getShop_id();
                            if (orderShopId != shop_id)
                                continue;

                            if (orderInfo.getStatus() != 1)
                                continue;

                            int order_id = orderInfo.getOrder_id();

                            Call<List<OrderDetail>> getOrderDetailCall = networkService.get_pk_orderdetail(order_id);
                            getOrderDetailCall.enqueue(new Callback<List<OrderDetail>>() {
                                @Override
                                public void onFailure(Call<List<OrderDetail>> call, Throwable t) {
                                }

                                @Override
                                public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                                    List<OrderDetail> orderDetailList = response.body();
                                    if (orderDetailList.size() != 0) {
                                        if (orderDetailList.size() > 1) {
                                            desc = orderDetailList.get(0).getMenu_name() + "외 " + (orderDetailList.size() - 1) + "개";
                                        } else {
                                            desc = orderDetailList.get(0).getMenu_name();
                                        }
                                    }

                                    adapter.addItem(Integer.toString(orderInfo.getOrder_id()), orderInfo.getOrder_time(), desc);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }
                    } else {
                        Log.d("debugging", "Error Message : " + response.errorBody());
                    }
                }
            });

        }

        return view;
    }

    public void reload() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }
}