package com.example.kioskserver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Button loginBtn;
    EditText EtId, EtPw;
    final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);

        EtId = (EditText)findViewById(R.id.et_id);
        EtPw = (EditText)findViewById(R.id.et_pw);
    }

    private void singIn(String email, String password) {

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            currentUser = mAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this, "로그인 성공" + "/" + currentUser.getEmail() + "/" + currentUser.getUid() ,Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
/*
                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
*/
                    }
                });
        // [END sign_in_with_email]

    }

    @Override
    public void onClick(View v) {
        String email = EtId.getText().toString();
        String password = EtPw.getText().toString();

        if (email.equals("")) {
            //TODO: 에러처리
            return;
        } else if (password.equals("")) {
            //TODO: 에러처리
        }
        singIn(email, password);
    }

}

