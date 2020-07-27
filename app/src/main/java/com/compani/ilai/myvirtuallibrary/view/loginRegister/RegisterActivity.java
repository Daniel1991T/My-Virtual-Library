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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.compani.ilai.myvirtuallibrary.Utils.LIBRARY_NAME_SP;
import static com.compani.ilai.myvirtuallibrary.Utils.PROFILE;
import static com.compani.ilai.myvirtuallibrary.Utils.USERNAME_SP;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "Register";

    private TextView txtWarningUsername, txtWarningEmail, txtWarningLibraryName, txtWarningPassword,
    txtWarningPassConfirm, txtRegisterToLogin;
    private Button btnRegister;

    private EditText eTxtUsername, eTxtEmail, eTxtLibraryName, eTxtPassword, eTxtPassConfirm;

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
                invisibleButton();
                if (isValidEmail() && isValidPassword()) {
                    createFirebaseUser();
                } else {
                    //TODO: invalid password or email
                    if (!isValidEmail()) {
                        txtWarningEmail.setText("Email invalid! Try again");
                        txtWarningEmail.setVisibility(View.VISIBLE);
                    }

                    if (!isValidPassword()) {
                        txtWarningPassword.setText("Password is short at 8 characters or no upper case");
                        txtWarningPassword.setVisibility(View.VISIBLE);
                    }
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
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email.toLowerCase());
        Log.d(TAG, "isValidEmail: " + matcher.matches() + " " + email);
        return matcher.matches();
    }

    private boolean isValidPassword() {

        String password = eTxtPassword.getText().toString().trim();
        String confirmPassword = eTxtPassConfirm.getText().toString().trim();
        if (!password.equals(confirmPassword)) {
            txtWarningPassConfirm.setText("Password is not same");
            txtWarningPassConfirm.setVisibility(View.VISIBLE);
            return false;
        }

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        /*
         * (?=.*[0-9]) - a digit must occur at least once
         * (?=.*[a-z]) - a lower case letter must occur at least once
         * (?=.*[A-Z]) - an upper case letter must occur at least once
         * (?=\\S+$) - no whitespace allowed in the entire string
         * .{8,} - at least 8 characters
         */
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        Log.d(TAG, "isValidPassword: "  + matcher.matches() + " " + password);
        return matcher.matches();
    }

    private void initView() {
        txtWarningUsername = findViewById(R.id.rgWarningUsername);
        txtWarningEmail = findViewById(R.id.rgTxtWarningEmail);
        txtWarningLibraryName = findViewById(R.id.rgTxtWarningLibraryName);
        txtWarningPassword = findViewById(R.id.rgTxtWarningPassword);
        txtWarningPassConfirm = findViewById(R.id.rgTxtWarningPassConfirm);
        txtRegisterToLogin = findViewById(R.id.btnTxtRegisteToLogin);

        btnRegister = findViewById(R.id.btnRegister);

        eTxtUsername = findViewById(R.id.rgUsernameETxt);
        eTxtEmail = findViewById(R.id.rgEmailETxt);
        eTxtLibraryName = findViewById(R.id.rgTxtLibraryName);
        eTxtPassword = findViewById(R.id.rgPasswordETxt);
        eTxtPassConfirm = findViewById(R.id.rgETxtPassConfirm);

        mAuth = FirebaseAuth.getInstance();

    }

    private void invisibleButton() {
        txtWarningEmail.setVisibility(View.GONE);
        txtWarningPassword.setVisibility(View.GONE);
        txtWarningPassConfirm.setVisibility(View.GONE);
        txtWarningLibraryName.setVisibility(View.GONE);
        txtWarningUsername.setVisibility(View.GONE);
    }

    private void saveUsernameLibraryName() {
        String username = eTxtUsername.getText().toString().trim();
        String libraryName = eTxtLibraryName.getText().toString().trim();

        prefs = getSharedPreferences(PROFILE, 0);
        prefs.edit().putString(USERNAME_SP, username).apply();
        prefs.edit().putString(LIBRARY_NAME_SP, libraryName).apply();

    }


}