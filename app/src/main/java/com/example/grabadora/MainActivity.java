package com.example.grabadora;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private ImageView btnGrabar;
    private ImageView btnDetener;
    private ImageView btnReproducir;
    private TextView tvTitulo;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private File file;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGrabar = findViewById(R.id.btn_grabar);
        btnDetener = findViewById(R.id.btn_detener);
        btnReproducir = findViewById(R.id.btn_reproducir);
        tvTitulo = findViewById(R.id.tv_titulo);
//        recorder = new MediaRecorder();

        btnDetener.setEnabled(false);
        btnGrabar.setOnClickListener(view -> {
            grabar();
        });
        btnDetener.setOnClickListener(view -> {
            detener();
        });

        btnReproducir.setOnClickListener(view -> {
            reproducir();
        });

        System.out.println("DIRECTORIO ::: "+Environment.getExternalStorageDirectory().getPath());

    }


    private void grabar(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory().getPath());
        try{
            file = File.createTempFile("temporal"+num+" - ",".3gp",path);
            recorder.setOutputFile(file.getAbsolutePath());
            recorder.prepare();
        }catch (IOException e){
            System.out.println(e);
        }
        recorder.start();
        tvTitulo.setText("Gabando");
        btnGrabar.setEnabled(false);
        btnReproducir.setEnabled(false);
        btnDetener.setEnabled(true);
        num++;
        btnGrabar.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_2));
    }

    public void detener(){
        player = new MediaPlayer();
        recorder.stop();
        recorder.release();
        try{
            player.setOnCompletionListener(this);
            player.setDataSource(file.getAbsolutePath());
            player.prepare();
        }catch (IOException e){

        }
        tvTitulo.setText("Detenido");
        btnGrabar.setEnabled(true);
        btnDetener.setEnabled(false);
        btnReproducir.setEnabled(true);
        btnGrabar.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_1));
    }

    private void reproducir(){
        player.start();
        tvTitulo.setText("Reproduciendo");
        btnGrabar.setEnabled(false);
        btnDetener.setEnabled(false);
        btnReproducir.setEnabled(false);
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        btnGrabar.setEnabled(true);
        btnDetener.setEnabled(true);
        btnReproducir.setEnabled(true);
        tvTitulo.setText("Listo");
    }
}