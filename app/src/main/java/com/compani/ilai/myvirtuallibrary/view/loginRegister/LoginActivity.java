package com.compani.ilai.myvirtuallibrary.view.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compani.ilai.myvirtuallibrary.MainActivity;
import com.compani.ilai.myvirtuallibrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtBtnLoginToRegister, txtWarningEmail, txtWarningPassword;
    private EditText eTxtEmail, eTxtPassword;

    private FirebaseAuth mAuth;
    private String  TAG = "MyApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButton();

        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        txtBtnLoginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void attemptLogin() {
        String email = eTxtEmail.getText().toString().trim();
        String password = eTxtPassword.getText().toString().trim();
        setVisibilityWarning();

        if (email.equals("")) {
            txtWarningEmail.setText("Field empty");
            txtWarningEmail.setVisibility(View.VISIBLE);
            return;
        }
        if (password.equals("")) {
            txtWarningPassword.setText("Field empty");
            txtWarningPassword.setVisibility(View.VISIBLE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail() onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail() onComplete: " + task.getException());
                    Toast.makeText(LoginActivity.this, "Login unsuccessful! \n " +
                            "Email or Password invalid", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void initButton() {
        btnLogin = findViewById(R.id.btnLogin);
        txtBtnLoginToRegister = findViewById(R.id.btnTxtLoginToRegister);
        eTxtEmail = findViewById(R.id.loginUsername);
        eTxtPassword = findViewById(R.id.eTxtLoginPassword);
        txtWarningEmail = findViewById(R.id.txtVwWarningEmail);
        txtWarningPassword = findViewById(R.id.txtVwWarningPassword);
    }

    private void setVisibilityWarning() {
        txtWarningPassword.setVisibility(View.GONE);
        txtWarningEmail.setVisibility(View.GONE);
    }
}