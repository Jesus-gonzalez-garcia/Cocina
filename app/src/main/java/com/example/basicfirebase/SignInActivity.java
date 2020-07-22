package com.example.basicfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtxtEmail;
    private EditText edtxtPass;
    private Button btnSignIn;
    private TextView txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        bindViews();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyFields()) {
                    signIn(edtxtEmail.getText().toString(), edtxtPass.getText().toString());
                } else {
                    Toast.makeText(SignInActivity.this, "Es necesario ingresar los datos solicitados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bindViews() {
        edtxtEmail = findViewById(R.id.edtxt_sign_in_email);
        edtxtPass = findViewById(R.id.edtxt_sign_in_pass);
        btnSignIn = findViewById(R.id.btn_sign_in);
        txtCreateAccount = findViewById(R.id.txt_create_account);
    }

    private boolean isEmptyFields() {
        return !edtxtEmail.getText().toString().isEmpty() && !edtxtPass.getText().toString().isEmpty();
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}