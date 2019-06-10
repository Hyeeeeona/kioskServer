package com.example.kioskserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopInfoSettingActivity extends AppCompatActivity {

    EditText etShopName, etShopTel, etIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info_setting);

        etShopName = (EditText)findViewById(R.id.et_shop_name);
        etShopTel = (EditText)findViewById(R.id.et_shop_tel);
        etIntroduction = (EditText)findViewById(R.id.et_introduction);

        if (ShopInfoDataFileIO.isExistShopInfoData(getApplicationContext())) {
            JSONObject jsonObject = ShopInfoDataFileIO.readShopInfoDataJson(getApplicationContext());
            try {
                etShopName.setText(jsonObject.getString("shop_name"));
                etShopTel.setText(jsonObject.getString("shop_tel"));
                etIntroduction.setText(jsonObject.getString("shop_introduction"));
            } catch(JSONException e) {
                //TODO
            }
        }
    }
}
