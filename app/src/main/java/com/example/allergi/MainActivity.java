package com.example.allergi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.allergi.accounting_activity.AccountingActivity;
import com.example.allergi.optimize_activity.OptimizeActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button optimize = findViewById(R.id.button_optimize_activity);

        optimize.setOnClickListener((v) -> {
            Intent intent = new Intent(this, OptimizeActivity.class);
            startActivity(intent);
        });

        Button accounting = findViewById(R.id.button_accounting_activity);

        accounting.setOnClickListener((v) -> {
            Intent intent = new Intent(this, AccountingActivity.class);
            startActivity(intent);
        });

    }

}