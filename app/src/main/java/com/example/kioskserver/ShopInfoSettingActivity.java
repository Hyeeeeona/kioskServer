package com.example.kioskserver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopInfoSettingActivity extends AppCompatActivity {

    EditText etShopName, etShopTel, etIntroduction, etShopAddress;
    Button btnShopBusinessHoursOpen, btnShopBusinessHoursClose;
    private NetworkService networkService;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    CheckBox[] cbPersonalDay = new CheckBox[7];
    CheckBox cbBusinessHoursAllday;
    String[] personalDayStrArr = {"일","월","화","수","목","금","토"};
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info_setting);

        mAuth = FirebaseAuth.getInstance();

        etShopName = (EditText) findViewById(R.id.et_shop_name);
        etShopTel = (EditText) findViewById(R.id.et_shop_tel);
        etIntroduction = (EditText) findViewById(R.id.et_introduction);
        etShopAddress = (EditText)findViewById(R.id.et_shop_address);

        btnShopBusinessHoursOpen = (Button) findViewById(R.id.btn_business_hours_open);
        btnShopBusinessHoursClose = (Button) findViewById(R.id.btn_business_hours_close);

        for (int i=0; i<7; i++) {
            int id = getResources().getIdentifier("cb_personal_day_"+i, "id", getPackageName());
            cbPersonalDay[i] = (CheckBox)findViewById(id);
        }

        cbBusinessHoursAllday = (CheckBox)findViewById(R.id.cb_business_hours_allday);
        cbBusinessHoursAllday.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    btnShopBusinessHoursOpen.setEnabled(false);
                    btnShopBusinessHoursClose.setEnabled(false);
                } else {
                    btnShopBusinessHoursOpen.setEnabled(true);
                    btnShopBusinessHoursClose.setEnabled(true);
                }
            }
        });

        btnShopBusinessHoursOpen.setOnClickListener(setBusinessHoursClickListener);
        btnShopBusinessHoursClose.setOnClickListener(setBusinessHoursClickListener);

        findViewById(R.id.btn_apply).setOnClickListener(shopInfoApplyClickListener);

        networkService = ApplicationController.getInstance().getNetworkService();

        if (ShopInfoDataFileIO.isExistShopInfoData(getApplicationContext())) {
            JSONObject jsonObject = ShopInfoDataFileIO.readShopInfoDataJson(getApplicationContext());
            try {
                shopId = Integer.parseInt(jsonObject.getString("shop_id"));
                etShopName.setText(jsonObject.getString("shop_name"));
                etShopTel.setText(jsonObject.getString("shop_tel"));
                etIntroduction.setText(jsonObject.getString("shop_introduction"));
                String businessHours = jsonObject.getString("shop_businesshours");
                etShopAddress.setText(jsonObject.getString("shop_address"));

                if (businessHours.equals("24시간")) {
                    cbBusinessHoursAllday.setChecked(true);
                    btnShopBusinessHoursOpen.setEnabled(false);
                    btnShopBusinessHoursClose.setEnabled(false);
                } else {
                    String[] tmp = businessHours.split("~");
                    if (tmp.length == 2) {
                        btnShopBusinessHoursOpen.setText(tmp[0]);
                        btnShopBusinessHoursClose.setText(tmp[1]);
                    }
                }

                String personalDay = jsonObject.getString("shop_personalday");
                String[] tmp = personalDay.split(",");
                for (int i=0; i < tmp.length; i++) {
                    //int idx = Arrays.binarySearch(personalDayStrArr, tmp[i]);
                    int idx=-1;
                    for (int j=0; j<personalDayStrArr.length; j++) {
                        if (tmp[i].equals(personalDayStrArr[j])) {
                            idx = j;
                            break;
                        }
                    }
                    if (idx != -1) {
                        cbPersonalDay[idx].setChecked(true);
                    }
                }
            } catch (JSONException e) {
                //TODO
                e.printStackTrace();
            }
        } else {
        }
/*
        networkService = ApplicationController.getInstance().getNetworkService();

        Call<List<ShopInfo>> getCall = networkService.get_shopinfo();
        getCall.enqueue(new Callback<List<ShopInfo>>() {
            @Override
            public void onFailure(Call<List<ShopInfo>> call, Throwable t) {
                Log.d("debugging", "Fail Message : " + t.getMessage());
            }

            @Override
            public void onResponse(Call<List<ShopInfo>> call, Response<List<ShopInfo>> response) {
                Boolean isExist = false;
                if (response.isSuccessful()) {
                    List<ShopInfo> shopinfoList = response.body();
                    for (ShopInfo shopinfo : shopinfoList) {
                        JSONObject jsonObject = ShopInfoDataFileIO.makeShopInfoDataJson(shopinfo);
                        try {
                            //ShopInfoDataFileIO.saveShopInfoDataJson(getApplicationContext(), jsonObject);
                            Log.d("debugging", jsonObject.getString("shop_name"));
                            break;
                        } catch (JSONException e) {

                        }
                    }
                } else {
                    Log.d("debugging", "Error Message : " + response.errorBody());
                }
                if (!isExist) {
                    Toast.makeText(ShopInfoSettingActivity.this, "매장 설정 필요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
    }

    Button.OnClickListener setBusinessHoursClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            TimePickerDialog mTimePicker;
            int hour = 0;
            int minute = 0;

            mTimePicker = new TimePickerDialog(ShopInfoSettingActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    //TODO: 입력값 검사 필요
                    String text = String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute);
                    switch (v.getId()) {
                        case R.id.btn_business_hours_open:
                            String close = btnShopBusinessHoursClose.getText().toString();
                            if(close.equals("Close")) {
                                btnShopBusinessHoursOpen.setText(text);
                                return;
                            }
                            String tmp[] = close.split(":");
                            if (Integer.parseInt(tmp[0]) < selectedHour) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShopInfoSettingActivity.this);
                                builder.setMessage("시작 시간은 끝나는 시간보다 크게 지정할 수 없습니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            btnShopBusinessHoursOpen.setText(text);
                            break;
                        case R.id.btn_business_hours_close:
                            String open = btnShopBusinessHoursOpen.getText().toString();
                            if(open.equals("Open")) {
                                btnShopBusinessHoursOpen.setText(text);
                                return;
                            }
                            tmp = open.split(":");
                            if (Integer.parseInt(tmp[0]) < selectedHour) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShopInfoSettingActivity.this);
                                builder.setMessage("끝나는 시간은 시작 시간보다 작게 지정할 수 없습니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                            btnShopBusinessHoursClose.setText(text);
                            break;
                    }
                }
            }, hour, minute, true); // true의 경우 24시간 형식의 TimePicker 출현
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
    };

    Button.OnClickListener shopInfoApplyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ShopInfo shopInfo = new ShopInfo();
            currentUser = mAuth.getCurrentUser();

            shopInfo.setUid(currentUser.getUid());
            shopInfo.setShopId(shopId);
            shopInfo.setShopName(etShopName.getText().toString());
            shopInfo.setShopTel(etShopTel.getText().toString());
            shopInfo.setShop_address(etShopAddress.getText().toString());
            if (cbBusinessHoursAllday.isChecked()) {
                shopInfo.setBusinessHours("24시간");
            } else {
                String businessHours = btnShopBusinessHoursOpen.getText().toString() + btnShopBusinessHoursClose.getText().toString();
                shopInfo.setBusinessHours(businessHours);
            }
            shopInfo.setIntroduction(etIntroduction.getText().toString());

            String personalDay = "";
            for (int i=0; i<7; i++) {
                if (cbPersonalDay[i].isChecked()) {
                    personalDay += personalDayStrArr[i];
                    personalDay += ",";
                }
            }
            personalDay = personalDay.substring(0, personalDay.length()-1);
            shopInfo.setPersonalDay(personalDay);

            //TODO: DB에 해당 매장이 존재하면 PATCH, 없으면 POST..
            if (shopId == 0) {
                Call<ShopInfo> getCall = networkService.post_shopinfo(shopInfo);
                getCall.enqueue(new Callback<ShopInfo>() {
                    @Override
                    public void onFailure(Call<ShopInfo> call, Throwable t) {
                        Log.d("debugging", "Fail Message : " + t.getMessage());
                    }

                    @Override
                    public void onResponse(Call<ShopInfo> call, Response<ShopInfo> response) {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = ShopInfoDataFileIO.makeShopInfoDataJson(response.body());
                            ShopInfoDataFileIO.saveShopInfoDataJson(getApplicationContext(), jsonObject);
                        } else {
                        }
                    }
                });
            } else {
                Call<ShopInfo> getCall = networkService.patch_shopinfo(shopId, shopInfo);
                getCall.enqueue(new Callback<ShopInfo>() {
                    @Override
                    public void onFailure(Call<ShopInfo> call, Throwable t) {
                        Log.d("debugging", "Fail Message : " + t.getMessage());
                    }

                    @Override
                    public void onResponse(Call<ShopInfo> call, Response<ShopInfo> response) {
                        if (response.isSuccessful()) {
                            Log.d("debugging", response.body().toString());

                            JSONObject jsonObject = ShopInfoDataFileIO.makeShopInfoDataJson(response.body());
                            Log.d("debugging", jsonObject.toString());

                        /*
                        try {
                            ShopInfoDataFileIO.saveShopInfoDataJson(getApplicationContext(), jsonObject);
                        } catch (JSONException e) {

                        }
                        */
                        } else {
                        }
                    }
                });
            }

            finish();
            startActivity(getIntent());
        }
    };
}
