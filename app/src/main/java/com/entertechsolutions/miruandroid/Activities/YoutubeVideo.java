package com.entertechsolutions.miruandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.w3c.dom.Text;

import dmax.dialog.SpotsDialog;

public class YoutubeVideo extends AppCompatActivity {

    private final String API_Key = "AIzaSyAe9_7NSrZhDS5mXMP7uQb2o5pXFAAkU9M ";
    String path,name;
    Button back_btn;
    YouTubePlayerView youTubePlayerView ;
    android.app.AlertDialog waitingDialog;
    TextView name1,name2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);
        Intent intent = getIntent();
        path = intent.getStringExtra("link");
        name = intent.getStringExtra("name");
        Log.e("Id   "," " + path);
        name1 = findViewById(R.id.nameVideo);
        name2 = findViewById(R.id.nameCheck);

        name2.setText(name);
        name1.setText(name);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
       // youTubePlayerView.enterFullScreen();
        //youTubePlayerView.exitFullScreen();
        //youTubePlayerView.isFullScreen();
       // youTubePlayerView.toggleFullScreen();
        getLifecycle().addObserver(youTubePlayerView);
        waitingDialog = new SpotsDialog.Builder()
                .setContext(YoutubeVideo.this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        back_btn = findViewById(R.id.videosBackBtn);
        back_btn.setOnClickListener(v -> onBackPressed());

        Start();
        /*for(int i = 0; i< 10; i++){
            if (path!=null) {
                Start();
                Toast.makeText(MyApplication.getContext(),"here ........",Toast.LENGTH_LONG).show();
            }
            Toast.makeText(YoutubeVideo.this,""+i,Toast.LENGTH_SHORT).show();
        }*/
    }

    private void Start(){

            waitingDialog.show();
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    waitingDialog.hide();
                    String videoId = "S0Q4gqBUs7c";
                    youTubePlayer.loadVideo(path, 0);
                }
            });



    }

}
