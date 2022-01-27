package com.example.resistor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    public static final String Tag=LoginActivity.class.getSimpleName();
    TextView forgotTextlink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mEmail=findViewById(R.id.edemaildn);
        mPassword=findViewById(R.id.edpassworddn);
        progressBar=findViewById(R.id.pgloaddn);
        fAuth=FirebaseAuth.getInstance();
        mLoginBtn=findViewById(R.id.btdn);
        forgotTextlink=findViewById(R.id.qmk);

        forgotTextlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.loginn,new SupportFragment()).addToBackStack(null).commit();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Chưa nhập email");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Chưa nhập mật khẩu");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Mật khẩu phải nhiều hơn 5 kí tự");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                        else {
                            Toast toast = Toast.makeText(LoginActivity.this, "Lỗi đăng nhập\nVui lòng kiểm tra lại email hoặc mật khẩu", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            if( v != null) v.setGravity(Gravity.CENTER);
                            toast.show();
                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = String.valueOf(mEmail.getText());
        PasswordValue = String.valueOf(mPassword.getText());
        System.out.println("onPause save name: " + UnameValue);
        System.out.println("onPause save password: " + PasswordValue);
        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_PASSWORD, PasswordValue);
        editor.commit();
    }
    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        mEmail.setText(UnameValue);
        mPassword.setText(PasswordValue);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onStart() {
        super.onStart();
        loadPreferences();
    }
}