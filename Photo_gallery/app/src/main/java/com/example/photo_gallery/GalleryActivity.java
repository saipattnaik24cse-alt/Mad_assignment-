package com.example.photo_gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.GridView;
import android.widget.Toast; // Toast add kiya debug ke liye
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView grid = findViewById(R.id.galleryGrid);
        ArrayList<String> imagePaths = new ArrayList<>();

        // FIX: Folder name wahi hona chahiye jo MainActivity mein hai (MyGalleryApp)
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyGalleryApp");

        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                        imagePaths.add(file.getAbsolutePath());
                    }
                }
            }
        }

        // Agar list khali hai toh user ko pata chale
        if (imagePaths.isEmpty()) {
            Toast.makeText(this, "No photos found in MyGalleryApp!", Toast.LENGTH_SHORT).show();
        }

        ImageAdapter adapter = new ImageAdapter(this, imagePaths);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(GalleryActivity.this, DetailActivity.class);
            intent.putExtra("path", imagePaths.get(position));
            startActivity(intent);
        });
    }
}