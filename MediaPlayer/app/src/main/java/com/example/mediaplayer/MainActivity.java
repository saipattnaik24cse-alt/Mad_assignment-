package com.example.mediaplayer;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView videoView = findViewById(R.id.videoView);

        // 1. HTTPS wali secure link use kar (Buffering fast hogi)
        String videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);

        // 2. Media Controls setup
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        // 3. Jab tak video load ho rahi hai, user ko pata chale
        Toast.makeText(this, "Buffering... Please wait", Toast.LENGTH_LONG).show();

        // 4. Jab ready ho jaye, tabhi start karo (Waiting problem solve)
        videoView.setOnPreparedListener(mp -> {
            videoView.start();
        });

        // 5. Agar internet slow hai ya link dead hai
        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(MainActivity.this, "Error playing video. Check Internet!", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}