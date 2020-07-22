package com.example.basicfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignOut;
    private Button btnContratoDeSoftware;
    private Button btnAvisoDePrivacidad;
    private Button btnManual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignOut = findViewById(R.id.btn_sign_out);
        btnContratoDeSoftware = findViewById(R.id.btn_contrato_de_software);
        btnAvisoDePrivacidad = findViewById(R.id.btn_aviso_de_privacidad);
        btnManual = findViewById(R.id.btn_manual);

        btnContratoDeSoftware.setOnClickListener(this);
        btnAvisoDePrivacidad.setOnClickListener(this);
        btnManual.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contrato_de_software:
                goToPDFView("contrato_de_software.pdf");
                break;
            case R.id.btn_aviso_de_privacidad:
                goToPDFView("aviso_de_privacidad.pdf");
                break;
            case R.id.btn_manual:
                goToPDFView("manual.pdf");
                break;
            case R.id.btn_sign_out:
                signOut();
                break;
        }
    }

    private void goToPDFView(String pdfAsset) {
        Bundle bndle = new Bundle();
        Intent intent = new Intent(MainActivity.this, PDFActivity.class);
        bndle.putString("data", pdfAsset);
        intent.putExtras(bndle);
        startActivity(intent);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}