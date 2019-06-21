package com.example.kioskserver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageMenuSettingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btn_menu_image, btn_apply, btn_cancel;
    EditText etMenuName, etMenuPrice;
    CheckBox cbUseHotorCold;
    RadioGroup rgHotorCold;
    private NetworkService networkService;
    String[] strHotorCold = {"Hot Only", "Ice Only", "Hot or Ice"};
    List<ShopMenu> shopMenuList;
    int menu_id = 0;

    Uri iuri;
    private final int REQ_CODE_PICK_PICTURE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu_setting);

        Intent intent = getIntent();
        final int shop_id = intent.getIntExtra("shop_id", 0);
        menu_id = intent.getIntExtra("menu_id", 0);

        networkService = ApplicationController.getInstance().getNetworkService();

        etMenuName = (EditText)findViewById(R.id.et_menu_name);
        etMenuPrice = (EditText)findViewById(R.id.et_menu_price);
        cbUseHotorCold = (CheckBox)findViewById(R.id.cb_hotorcold);
        rgHotorCold = (RadioGroup)findViewById(R.id.rg_hotorcold);
        if (cbUseHotorCold.isChecked() == false) {
            for(int i = 0; i < rgHotorCold.getChildCount(); i++){
                ((RadioButton)rgHotorCold.getChildAt(i)).setEnabled(false);
            }
        }
        cbUseHotorCold.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = ((CheckBox) v).isChecked();
                for (int i = 0; i < rgHotorCold.getChildCount(); i++) {
                    ((RadioButton) rgHotorCold.getChildAt(i)).setEnabled(isChecked);
                    if (i==0) {
                        ((RadioButton) rgHotorCold.getChildAt(i)).setChecked(true);

                    }
                }
            }
        });

        imageView = (ImageView)findViewById(R.id.iv_menu_image);
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        imageView.setClipToOutline(true);

        btn_menu_image = (Button)findViewById(R.id.btn_menu_image);
        btn_apply = (Button)findViewById(R.id.btn_apply);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        btn_menu_image.setOnClickListener(this);
        btn_apply.setOnClickListener(this);

        if (shop_id != 0 && menu_id != 0) {
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
                            if (shopMenu.getId() == menu_id) {
                                etMenuName.setText(shopMenu.getMenu_name());
                                etMenuPrice.setText(Integer.toString(shopMenu.getMenu_price()));
                                int hotorcold = shopMenu.getHotorcold();
                                if (hotorcold == 0) {
                                    cbUseHotorCold.setChecked(false);
                                } else {
                                    cbUseHotorCold.setChecked(true);
                                    for (int i = 0; i < rgHotorCold.getChildCount(); i++) {
                                        ((RadioButton) rgHotorCold.getChildAt(i)).setEnabled(true);
                                    }
                                    ((RadioButton) rgHotorCold.getChildAt(shopMenu.getHotorcold() - 1)).setChecked(true);
                                }
                                break;
                            }
                        }
                    } else {
                        Log.d("debugging", "Error Message : " + response.errorBody());
                    }
                }
            });

        }




    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_menu_image) {
            /* 갤러리에서 사진 선택 */
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType(MediaStore.Images.Media.CONTENT_TYPE);
            i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, REQ_CODE_PICK_PICTURE);
        } else if (id == R.id.btn_cancel) {
            startActivity(new Intent(ManageMenuSettingActivity.this, ManageMenuActivity.class));
            finish();
        } else if (id == R.id.btn_apply) {
            ShopMenu shopMenu;

            int shop_id = ShopInfoDataFileIO.getShopId(getApplicationContext());
            String menu_name = etMenuName.getText().toString();
            int hotorcold = 0;
            if (cbUseHotorCold.isChecked()) {
                for (int i = 0; i < rgHotorCold.getChildCount(); i++) {
                    if (((RadioButton) rgHotorCold.getChildAt(i)).isChecked()) {
                        hotorcold = i+1;
                        break;
                    }
                }
            }
            String menu_price = etMenuPrice.getText().toString();

            if (menu_id == 0) {
                shopMenu = new ShopMenu(0, shop_id, menu_name, null, hotorcold, Integer.parseInt(menu_price));
                Call<ShopMenu> getCall = networkService.post_shopmenu(shopMenu);

                getCall.enqueue(new Callback<ShopMenu>() {
                    @Override
                    public void onFailure(Call<ShopMenu> call, Throwable t) {
                        Log.d("debugging", "Fail Message : " + t.getMessage());
                    }

                    @Override
                    public void onResponse(Call<ShopMenu> call, Response<ShopMenu> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(getApplicationContext(), "추가 실패", Toast.LENGTH_SHORT).show();
                            Log.d("debugging", "Error Message : " + response.errorBody());
                        }

                        startActivity(new Intent(ManageMenuSettingActivity.this, ManageMenuActivity.class));
                        finish();

                    }
                });
            } else {
                shopMenu = new ShopMenu(menu_id, shop_id, menu_name, null, hotorcold, Integer.parseInt(menu_price));
                Call<ShopMenu> getCall = networkService.patch_shopmenu(menu_id, shopMenu);

                getCall.enqueue(new Callback<ShopMenu>() {
                    @Override
                    public void onFailure(Call<ShopMenu> call, Throwable t) {
                        Log.d("debugging", "Fail Message : " + t.getMessage());
                    }

                    @Override
                    public void onResponse(Call<ShopMenu> call, Response<ShopMenu> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "변경 성공", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "변경 실패", Toast.LENGTH_SHORT).show();
                            Log.d("debugging", "Error Message : " + response.errorBody());
                        }

                        startActivity(new Intent(ManageMenuSettingActivity.this, ManageMenuActivity.class));
                        finish();

                    }
                });
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        iuri = intent.getData();

        try {
            MediaStore.Images.Media.getBitmap(getContentResolver(), iuri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(requestCode == REQ_CODE_PICK_PICTURE){
            if(resultCode == Activity.RESULT_OK){
                imageView.setImageURI(iuri);
                imageView.setClipToOutline(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }

}
