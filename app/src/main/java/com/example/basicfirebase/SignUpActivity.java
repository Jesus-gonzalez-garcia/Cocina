package com.example.basicfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText edtxtName;
    private EditText edtxtLastName;
    private EditText edtxtEmail;
    private EditText edtxtPass;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        bindViews();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyFields()) {
                    User user = new User(edtxtName.getText().toString(), edtxtLastName.getText().toString(), edtxtEmail.getText().toString(), edtxtPass.getText().toString());
                    signUp(user);
                } else {
                    Toast.makeText(SignUpActivity.this, "Es necesario ingresar los datos solicitados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindViews() {
        edtxtName = findViewById(R.id.edtxt_sign_up_name);
        edtxtLastName = findViewById(R.id.edtxt_sign_up_last_name);
        edtxtEmail = findViewById(R.id.edtxt_sign_up_email);
        edtxtPass = findViewById(R.id.edtxt_sign_up_pass);
        btnSignUp = findViewById(R.id.btn_sign_up);
    }

    private boolean isEmptyFields() {
        return !edtxtName.getText().toString().isEmpty() && !edtxtLastName.getText().toString().isEmpty() && !edtxtEmail.getText().toString().isEmpty() && !edtxtPass.getText().toString().isEmpty();
    }

    private void signUp(final User user) {
        showProgressDialog();
        saveUserInDB(user);
    }

    private void saveUserInDB(final User user) {
        Map<String, String> userDB = new HashMap<>();
        userDB.put("Name", user.getName());
        userDB.put("LastName", user.getLastname());
        userDB.put("Email", user.getEmail());
        userDB.put("Password", user.getPassword());

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        hideProgressDialog();
                        createUserWithEmailAndPassword(user);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createUserWithEmailAndPassword(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Registrando Usuario");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

}