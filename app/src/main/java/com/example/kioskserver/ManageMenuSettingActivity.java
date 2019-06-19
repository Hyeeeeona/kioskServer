package com.example.kioskserver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class ManageMenuSettingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button btn_menu_image, btn_apply;
    Uri iuri;
    private final int REQ_CODE_PICK_PICTURE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu_setting);

        imageView = (ImageView)findViewById(R.id.iv_menu_image);
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        imageView.setClipToOutline(true);

        btn_menu_image = (Button)findViewById(R.id.btn_menu_image);
        btn_apply = (Button)findViewById(R.id.btn_apply);

        btn_menu_image.setOnClickListener(this);
        btn_apply.setOnClickListener(this);

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
        } else if (id == R.id.btn_apply) {
            /* 추가 버튼 클릭 시 이미지 uri 저장은 아래 String으로 */
            String img_uri = iuri.toString();
            //디비 이미지 호출 시에는 imgView.setImageURI(Uri.parse(pic_uri))
            Toast.makeText(this,  "img = "+img_uri, Toast.LENGTH_LONG).show();
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
