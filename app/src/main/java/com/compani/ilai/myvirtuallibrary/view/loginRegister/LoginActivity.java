package com.compani.ilai.myvirtuallibrary.view.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compani.ilai.myvirtuallibrary.MainActivity;
import com.compani.ilai.myvirtuallibrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.compani.ilai.myvirtuallibrary.Utils.EMAIL_REGEX;
import static com.compani.ilai.myvirtuallibrary.Utils.PASSWORD_REGEX;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtBtnLoginToRegister;
    private EditText iTxtPassword;
    private TextInputEditText eTxtEmail;
    private TextInputLayout iTxtLayoutEmail, iTxtLayoutPassword;

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
        String password = iTxtPassword.getText().toString().trim();

        if (!validateEmail() | !validatePassword()) {
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

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail() {
        String email = eTxtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            iTxtLayoutEmail.setError("Enter Email");
            requestFocus(eTxtEmail);
            return false;
        } else {
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email.toLowerCase());
            Log.d(TAG, "isValidEmail: " + matcher.matches() + " " + email);
            if (!matcher.matches()) {
                requestFocus(eTxtEmail);
                iTxtLayoutEmail.setError("Email invalid!");
                return false;
            } else {
                iTxtLayoutEmail.setError(null);
                return true;
            }
        }
    }

    private boolean validatePassword() {
        String password = iTxtPassword.getText().toString().trim();
        if (password.isEmpty()) {
            iTxtLayoutPassword.setError("Enter Password");
            return false;
        } else {
            Pattern pattern = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                iTxtLayoutPassword.setError("Password invalid");
                return false;
            } else {
                iTxtLayoutPassword.setError(null);
                return true;
            }
        }
    }

    private void initButton() {
        btnLogin = findViewById(R.id.btnLogin);
        txtBtnLoginToRegister = findViewById(R.id.btnTxtLoginToRegister);
        eTxtEmail = findViewById(R.id.loginUsername);
        iTxtLayoutEmail = findViewById(R.id.loginUsernameLayout);
        iTxtPassword = findViewById(R.id.eTxtLoginPassword);
        iTxtLayoutPassword = findViewById(R.id.iTxtLayoutLoginPassword);
    }
}