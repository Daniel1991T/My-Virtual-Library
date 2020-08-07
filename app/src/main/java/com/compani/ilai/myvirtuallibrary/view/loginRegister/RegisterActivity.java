package com.compani.ilai.myvirtuallibrary.view.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.compani.ilai.myvirtuallibrary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.compani.ilai.myvirtuallibrary.Utils.EMAIL_REGEX;
import static com.compani.ilai.myvirtuallibrary.Utils.LIBRARY_NAME_SP;
import static com.compani.ilai.myvirtuallibrary.Utils.PROFILE;
import static com.compani.ilai.myvirtuallibrary.Utils.USERNAME_SP;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "Register";

    private TextView txtRegisterToLogin;
    private Button btnRegister;

    private EditText eTxtUsername, eTxtEmail, eTxtLibraryName, eTxtPassword, eTxtPassConfirm;
    private TextInputLayout iTxtLayoutUsername, iTxtLayoutEmail, iTxtLayoutLibraryName, iTxtLayoutPassword,
    iTxtLayoutPasswordConfirm;

    private FirebaseAuth mAuth;

    public static SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEmail() && isValidPassword() && isSamePassword()
                && !isEmpty(eTxtUsername.getText().toString().trim(), iTxtLayoutUsername)
                && !isEmpty(eTxtLibraryName.getText().toString().trim(), iTxtLayoutLibraryName)) {
                    createFirebaseUser();
                }
            }
        });


        txtRegisterToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createFirebaseUser() {
        //
        String email = eTxtEmail.getText().toString().trim();
        String password = eTxtPassword.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d(TAG, "user creation failed");
                } else {
                    saveUsernameLibraryName();
                    Toast.makeText(RegisterActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidEmail() {
        String email = eTxtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            iTxtLayoutEmail.setError("Enter Email");
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email.toLowerCase());
        Log.d(TAG, "isValidEmail: " + matcher.matches() + " " + email);
        if (!matcher.matches()) {
            iTxtLayoutEmail.setError("Email invalid!");
            return false;
        } else {
            iTxtLayoutEmail.setError(null);
            return true;
        }
    }

    public boolean isValidPassword() {
        String password = eTxtPassword.getText().toString().trim();
        Pattern specialCharPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Pattern lowerCasePattern = Pattern.compile("[a-z ]");
        Pattern digitCasePattern = Pattern.compile("[0-9]");

        if (password.length() < 8) {
            iTxtLayoutPassword.setError("Password length must have al least 8 character!");
            return false;
        } else {
            iTxtLayoutPassword.setError(null);
        }

        if (!specialCharPattern.matcher(password).find()) {
            iTxtLayoutPassword.setError("Password must have at least one special character!");
            return false;
        } else {
            iTxtLayoutPassword.setError(null);
        }

        if (!upperCasePattern.matcher(password).find()) {
            iTxtLayoutPassword.setError("Password must have at least one uppercase character!");
            return false;
        } else {
            iTxtLayoutPassword.setError(null);
        }

        if (!lowerCasePattern.matcher(password).find()) {
            iTxtLayoutPassword.setError("Password must have at least one lowercase character!");
            return false;
        } else {
            iTxtLayoutPassword.setError(null);
        }

        if (!digitCasePattern.matcher(password).find()) {
            iTxtLayoutPassword.setError("Password must have at least one digit character!");
            return false;
        } else {
            iTxtLayoutPassword.setError(null);
        }
        return true;
    }

    private boolean isSamePassword() {
        String password = eTxtPassword.getText().toString().trim();
        String confirmPassword = eTxtPassConfirm.getText().toString().trim();
        if (!password.equals(confirmPassword)) {
            iTxtLayoutPassword.setError("Passwords do not match");
            iTxtLayoutPasswordConfirm.setError("Passwords do not match");
            return false;
        } else {
            iTxtLayoutPasswordConfirm.setError(null);
            iTxtLayoutPassword.setError(null);
        }
        return true;
    }

    private boolean isEmpty(String field, TextInputLayout inputLayout) {
        if (field.isEmpty()) {
            inputLayout.setError("Field empty");
            return true;
        }
        else return false;
    }

    private void initView() {
        txtRegisterToLogin = findViewById(R.id.btnTxtRegisteToLogin);
        iTxtLayoutEmail = findViewById(R.id.iTxtLayoutRegEmail);
        iTxtLayoutUsername = findViewById(R.id.iTxtLayoutRegUsername);
        iTxtLayoutLibraryName = findViewById(R.id.iTxtLayoutRegLibraryName);
        iTxtLayoutPassword = findViewById(R.id.iTxtLayoutRegPassword);
        iTxtLayoutPasswordConfirm = findViewById(R.id.iTxtLayoutRegPasswordConfirm);

        btnRegister = findViewById(R.id.btnRegister);

        eTxtUsername = findViewById(R.id.rgUsernameETxt);
        eTxtEmail = findViewById(R.id.rgEmailETxt);
        eTxtLibraryName = findViewById(R.id.rgTxtLibraryName);
        eTxtPassword = findViewById(R.id.rgPasswordETxt);
        eTxtPassConfirm = findViewById(R.id.rgETxtPassConfirm);

        mAuth = FirebaseAuth.getInstance();

    }


    private void saveUsernameLibraryName() {
        String username = eTxtUsername.getText().toString().trim();
        String libraryName = eTxtLibraryName.getText().toString().trim();

        prefs = getSharedPreferences(PROFILE, 0);
        prefs.edit().putString(USERNAME_SP, username).apply();
        prefs.edit().putString(LIBRARY_NAME_SP, libraryName).apply();

    }


}