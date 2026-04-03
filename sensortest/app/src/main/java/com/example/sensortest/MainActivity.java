package com.example.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensortest.R;

/**
 * Q3: Sensor Data Display Application
 * Author: sai pattnaik
 * Sensors: Accelerometer, Light, Proximity
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, lightSensor, proximitySensor;
    private TextView accelText, lightText, proxText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI display components
        accelText = findViewById(R.id.accelText);
        lightText = findViewById(R.id.lightText);
        proxText = findViewById(R.id.proxText);

        // Access the system Sensor Manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Define the required sensors from the manager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register listeners when the app is in the foreground
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        if (lightSensor != null)
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI);
        if (proximitySensor != null)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister listeners to conserve battery when app is not visible
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Handle Accelerometer updates
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            String data = "X: " + String.format("%.2f", event.values[0]) +
                    "\nY: " + String.format("%.2f", event.values[1]) +
                    "\nZ: " + String.format("%.2f", event.values[2]);
            accelText.setText(data);
        }
        // Handle Light Sensor updates
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightText.setText("Intensity: " + event.values[0] + " lx");
        }
        // Handle Proximity Sensor updates
        else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proxText.setText("Distance: " + event.values[0] + " cm");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not implemented for this basic data display
    }
}