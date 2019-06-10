package com.example.kioskserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.kioskserver.ShopInfoDataFileIO;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FragmentManager fragmentManager;
    private AcceptFragment acceptFragment;
    private WaitConfirmFragment waitConfirmFragment;
    Button btnLogout;
    private NetworkService networkService;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_wait_confirm:
                    transaction.replace(R.id.framelayout, waitConfirmFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_accept:
                    transaction.replace(R.id.framelayout, acceptFragment).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        fragmentManager = getSupportFragmentManager();
        acceptFragment = new AcceptFragment();
        waitConfirmFragment = new WaitConfirmFragment();
        fragmentManager = getSupportFragmentManager();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, waitConfirmFragment).commitAllowingStateLoss();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ApplicationController application = ApplicationController.getInstance();
        application.buildNetworkService("10.0.2.2", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(MainActivity.this, "현재 로그인된 사용자 : " +currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            if (!ShopInfoDataFileIO.isExistShopInfoData(getApplicationContext())) {
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
                            for (ShopInfo shopinfo : shopinfoList ) {
                                if (shopinfo.getUid().equals(currentUser.getUid())) {
                                    Log.d("debugging", "찾았당");
                                    JSONObject jsonObject = ShopInfoDataFileIO.makeShopInfoDataJson(shopinfo);
                                    ShopInfoDataFileIO.saveShopInfoDataJson(getApplicationContext(), jsonObject);
                                    isExist = true;
                                    break;
                                }
                            }
                        } else {
                            Log.d("debugging", "Error Message : " + response.errorBody());
                        }
                        if (!isExist) {
                            Toast.makeText(MainActivity.this, "매장 설정 필요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //uid query
                //  if exist
                //    save to internal storage
                //  else
                //    need to set shop info

            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_shop_state:
                Toast.makeText(getApplicationContext(), "영업 상태",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_set_menu:
                startActivity(new Intent(MainActivity.this, ManageMenuActivity.class));
                return true;
            case R.id.menu_set_shop_info:
                startActivity(new Intent(MainActivity.this, ShopInfoSettingActivity.class));
                return true;
            case R.id.menu_logout:
                new AlertDialog.Builder(this)
                        .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mAuth.signOut();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
