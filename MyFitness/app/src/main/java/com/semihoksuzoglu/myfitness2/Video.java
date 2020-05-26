package com.semihoksuzoglu.myfitness2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class Video extends AppCompatActivity {
    public VideoView videoView;
    public Button geri;


    private FirebaseAuth firebaseAuth;
    FirebaseUser curUser;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();

        String name=getIntent().getStringExtra("ifade");
        geri=(Button) findViewById(R.id.geri);


        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(Video.this,MainActivity.class);
                startActivity(yeni);

            }
        });


if(name.equals("gun1")) {


    videoView = (VideoView) findViewById(R.id.videoView);
    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.vide1;
    Uri uri = Uri.parse(videoPath);
    videoView.setVideoURI(uri);
    MediaController mediaController = new MediaController(this);
    videoView.setMediaController(mediaController);
    mediaController.setAnchorView(videoView);
    videoView.setOnCompletionListener(videoCompletionListener);


}


else if (name.equals("gun2")){
    videoView = (VideoView) findViewById(R.id.videoView);
    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video2;
    Uri uri = Uri.parse(videoPath);
    videoView.setVideoURI(uri);
    MediaController mediaController = new MediaController(this);
    videoView.setMediaController(mediaController);
    mediaController.setAnchorView(videoView);
    videoView.setOnCompletionListener(videoCompletionListener);

}

else if (name.equals("gun3")){
    videoView = (VideoView) findViewById(R.id.videoView);
    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video3;
    Uri uri = Uri.parse(videoPath);
    videoView.setVideoURI(uri);
    MediaController mediaController = new MediaController(this);
    videoView.setMediaController(mediaController);
    mediaController.setAnchorView(videoView);
    videoView.setOnCompletionListener(videoCompletionListener);


}

else if (name.equals("gun4")){
    videoView = (VideoView) findViewById(R.id.videoView);
    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video4;
    Uri uri = Uri.parse(videoPath);
    videoView.setVideoURI(uri);
    MediaController mediaController = new MediaController(this);
    videoView.setMediaController(mediaController);
    mediaController.setAnchorView(videoView);
    videoView.setOnCompletionListener(videoCompletionListener);

}

else if (name.equals("gun5")){

    videoView = (VideoView) findViewById(R.id.videoView);
    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video5;
    Uri uri = Uri.parse(videoPath);
    videoView.setVideoURI(uri);
    MediaController mediaController = new MediaController(this);
    videoView.setMediaController(mediaController);
    mediaController.setAnchorView(videoView);
    videoView.setOnCompletionListener(videoCompletionListener);

}

else if (name.equals("gun6")){

    videoView = (VideoView) findViewById(R.id.videoView);
    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video6;
    Uri uri = Uri.parse(videoPath);
    videoView.setVideoURI(uri);
    MediaController mediaController = new MediaController(this);
    videoView.setMediaController(mediaController);
    mediaController.setAnchorView(videoView);
    videoView.setOnCompletionListener(videoCompletionListener);
}



    }

    MediaPlayer.OnCompletionListener videoCompletionListener  = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            int dakika = mediaPlayer.getDuration() / (1000 * 60);
            int kalori = 15 * dakika;

            updatecurrentLevel(kalori, dakika);
        }
    };

    void updatecurrentLevel(int kalori, int dakika) {
        int egzersiz = getIntent().getIntExtra("egzersiz" , 0) + 1;
        int kaloriP = kalori + getIntent().getIntExtra("kalori" , 0);
        int dakikaP = dakika + getIntent().getIntExtra("dakika" , 0);

        HashMap<String,Object> MyEgzersiz = new HashMap<>();
        MyEgzersiz.put("kalori", kaloriP);
        MyEgzersiz.put("dakika", dakikaP);
        MyEgzersiz.put("egzersiz", egzersiz);

        firebaseFirestore.collection("Egzersiz").document(curUser.getUid()).set(MyEgzersiz);
    }

}
