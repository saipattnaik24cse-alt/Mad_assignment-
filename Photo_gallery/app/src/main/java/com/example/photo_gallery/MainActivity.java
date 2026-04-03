package com.example.photo_gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection; // Naya Import
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTake = findViewById(R.id.btnTake);
        Button btnGallery = findViewById(R.id.btnGallery);

        btnTake.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        });

        btnGallery.setOnClickListener(v -> {
            startActivity(new Intent(this, GalleryActivity.class));
        });
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            saveToFolder(bmp);
        }
    }

    private void saveToFolder(Bitmap bmp) {
        // Folder name "MyGalleryApp" - Isse hi GalleryActivity mein bhi rakhna!
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyGalleryApp");
        if (!path.exists()) path.mkdirs();

        File file = new File(path, "IMG_" + System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            // *** YE LINE ZAROORI HAI: Taki Gallery Activity photo ko dhoond sake ***
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);

            Toast.makeText(this, "Photo Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
        }
    }
}