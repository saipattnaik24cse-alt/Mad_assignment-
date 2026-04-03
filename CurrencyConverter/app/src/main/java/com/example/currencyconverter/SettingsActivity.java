package com.example.currencyconverter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout setContainer;
    TextView setHeading;
    Switch modeToggle;
    ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setContainer = findViewById(R.id.setContainer);
        setHeading = findViewById(R.id.setHeading);
        modeToggle = findViewById(R.id.modeToggle);
        backIcon = findViewById(R.id.backIcon);

        SharedPreferences sp = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDark = sp.getBoolean("dark", false);
        modeToggle.setChecked(isDark);
        updateUI(isDark);

        modeToggle.setOnCheckedChangeListener((v, checked) -> {
            sp.edit().putBoolean("dark", checked).apply();
            updateUI(checked);
        });
        backIcon.setOnClickListener(v -> finish());
    }

    private void updateUI(boolean dark) {
        String bg = dark ? "#000000" : "#FFFFFF";
        String txt = dark ? "#FFFFFF" : "#000000";
        setContainer.setBackgroundColor(Color.parseColor(bg));
        setHeading.setTextColor(Color.parseColor(txt));
        modeToggle.setTextColor(Color.parseColor(txt));
        backIcon.setColorFilter(Color.parseColor(txt));
    }
}