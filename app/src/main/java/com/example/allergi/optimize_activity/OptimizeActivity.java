package com.example.allergi.optimize_activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.allergi.MainActivity;
import com.example.allergi.R;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;
import com.example.allergi.optimize_activity.list_allergy.OptimizeFragmentListAllergy;
import com.example.allergi.optimize_activity.result_calcus.OptimizeResultCalculsAdapter;
import com.example.allergi.optimize_activity.result_calcus.OptimizeResultCalculsFragment;
import com.example.allergi.utils.StaticSharedPreferences;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OptimizeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_accounting), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new OptimizeFragmentListAllergy())
                    .commit();
        }

        ImageButton btnExit = findViewById(R.id.activity_button_exit);
        ImageButton btnList = findViewById(R.id.activity_button_list);
        ImageButton btnResult = findViewById(R.id.activity_button_result);

        btnExit.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        btnResult.setOnClickListener(v -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof OptimizeFragmentListAllergy) {
                int maxNegative = ((OptimizeFragmentListAllergy) currentFragment).getMaxNegative();

                Type typeList = new TypeToken<List<AllergenOptimizeDTO>>(){}.getType();
                List<AllergenOptimizeDTO> list = StaticSharedPreferences.getObject(getString(R.string.storage_file), getString(R.string.key_list_optimize_allergy), typeList, this);


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, OptimizeResultCalculsFragment.newInstance(list, maxNegative))
                        .commit();
            }
        });

        btnList.setOnClickListener(v -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof OptimizeResultCalculsFragment) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OptimizeFragmentListAllergy())
                        .commit();
            }
        });
    }
}
