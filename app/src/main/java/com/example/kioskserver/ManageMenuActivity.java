package com.example.kioskserver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageMenuActivity extends AppCompatActivity {

    ListView listview;
    ListViewMenuAdapter adapter;
    private NetworkService networkService;
    String[] strHotorCold = {"Hot Only", "Ice Only", "Hot or Ice"};
    int selectedPos = -1;
    List<ShopMenu> shopMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMenuActivity.this, ManageMenuSettingActivity.class));
                finish();
            }
        });

        // Adapter 생성
        adapter = new ListViewMenuAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prefix = "주문번호 : ";
                String desc = ((ListViewMenuItem)adapter.getItem(position)).getDesc();
                String title = ((ListViewMenuItem)adapter.getItem(position)).getTitle();
                String price = ((ListViewMenuItem)adapter.getItem(position)).getPrice();
                int menu_id = 0;

                Intent intent = new Intent(ManageMenuActivity.this, ManageMenuSettingActivity.class);

                for ( ShopMenu shopMenu : shopMenuList) {
                    if(shopMenu.getMenu_name().equals(title)) {
                        intent.putExtra("shop_id", shopMenu.getShop_id());
                        intent.putExtra("menu_id", shopMenu.getId());
                        break;
                    }
                }
                startActivity(intent);
                finish();

            }
        });

        networkService = ApplicationController.getInstance().getNetworkService();

        final int shop_id = ShopInfoDataFileIO.getShopId(getApplicationContext());
        Log.d("hyeona", "shop_id : " + shop_id);
        if (shop_id != -1) {
            Call<List<ShopMenu>> getCall = networkService.get_pk_shopmenu(shop_id);

            getCall.enqueue(new Callback<List<ShopMenu>>() {
                @Override
                public void onFailure(Call<List<ShopMenu>> call, Throwable t) {
                    Log.d("debugging", "Fail Message : " + t.getMessage());
                }

                @Override
                public void onResponse(Call<List<ShopMenu>> call, Response<List<ShopMenu>> response) {
                    if (response.isSuccessful()) {
                        shopMenuList = response.body();

                        for (final ShopMenu shopMenu : shopMenuList) {
                            String title = shopMenu.getMenu_name();
                            int price = shopMenu.getMenu_price();
                            //desc
                            //price
                            int hotorcold = shopMenu.getHotorcold();
                            String desc = "";
                            if (hotorcold != 0) {
                                desc += strHotorCold[hotorcold-1];
                            }
                            adapter.addItem(title, desc, price);
                            adapter.notifyDataSetChanged(); //TODO 변경된게 있을때만 바꾸자.
                        }
                    } else {
                        Log.d("debugging", "Error Message : " + response.errorBody());
                    }
                }
            });

        }

    }
}
