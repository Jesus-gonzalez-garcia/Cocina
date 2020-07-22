package com.example.basicfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFActivity extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f);
        pdfView = findViewById(R.id.pdf_view);

        Bundle bndle = getIntent().getExtras();
        String assetName = bndle.getString("data");
        pdfView.fromAsset(assetName).load();
    }
}