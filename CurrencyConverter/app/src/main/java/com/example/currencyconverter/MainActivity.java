package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.ColorStateList;

public class MainActivity extends AppCompatActivity {
    RelativeLayout mainLayout;
    TextView titleText, resultText, equalSign;
    EditText amountInput;
    Button convertBtn;
    ImageView settingsBtn;
    Spinner spinnerFrom, spinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        titleText = findViewById(R.id.titleText);
        resultText = findViewById(R.id.resultText);
        equalSign = findViewById(R.id.equalSign);
        amountInput = findViewById(R.id.amountInput);
        convertBtn = findViewById(R.id.convertBtn);
        settingsBtn = findViewById(R.id.settingsBtn);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);

        String[] options = {"INR", "USD", "JPY", "EUR"};
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adp);
        spinnerTo.setAdapter(adp);

        applyTheme();

        settingsBtn.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        convertBtn.setOnClickListener(v -> {
            String val = amountInput.getText().toString();
            if (val.isEmpty()) return;
            double amount = Double.parseDouble(val);
            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            // Conversion Logic
            double inUSD = 0;
            if (from.equals("INR")) inUSD = amount / 83.0;
            else if (from.equals("JPY")) inUSD = amount / 151.0;
            else if (from.equals("EUR")) inUSD = amount / 0.92;
            else inUSD = amount;

            double result = 0;
            if (to.equals("INR")) result = inUSD * 83.0;
            else if (to.equals("JPY")) result = inUSD * 151.0;
            else if (to.equals("EUR")) result = inUSD * 0.92;
            else result = inUSD;

            resultText.setText(String.format("%.2f", result));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyTheme();
    }

    private void applyTheme() {
        SharedPreferences sp = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean dark = sp.getBoolean("dark", false);

        // Colors for Sai (Black & White)
        String bgColor = dark ? "#000000" : "#FFFFFF";
        String txtColor = dark ? "#FFFFFF" : "#000000";
        String boxColor = dark ? "#1A1A1A" : "#F2F2F2";

        mainLayout.setBackgroundColor(Color.parseColor(bgColor));
        titleText.setTextColor(Color.parseColor(txtColor));
        equalSign.setTextColor(Color.parseColor(txtColor));
        resultText.setTextColor(Color.parseColor(txtColor));
        amountInput.setTextColor(Color.parseColor(txtColor));
        amountInput.setHintTextColor(Color.parseColor(dark ? "#888888" : "#AAAAAA"));

        // Update Button
        convertBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(txtColor)));
        convertBtn.setTextColor(Color.parseColor(bgColor));

        // Update Icon
        settingsBtn.setColorFilter(Color.parseColor(txtColor));
    }
}