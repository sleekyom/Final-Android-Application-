package com.example.shoppinglistmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView signIn;
    private Button btnSignUp;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private ProgressBar mBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth=FirebaseAuth.getInstance();

        mBar= new ProgressBar(this);

        email=findViewById(R.id.email_signUp);
        password=findViewById(R.id.password_SignUp);
        btnSignUp=findViewById(R.id.btn_signUp);

        signIn=findViewById(R.id.signIn_txt);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail=email.getText().toString().trim();
                String mPassword=password.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)){
                    email.setError("Enter your email");
                    return;
                }

                if (TextUtils.isEmpty(mPassword)){
                    password.setError("Enter your password");
                    return;
                }

                mDialog.setMessage("Processing...");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
