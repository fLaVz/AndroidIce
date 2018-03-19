package com.androidapp;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import mp3App.*;

public class Client extends AppCompatActivity {

	MediaPlayer mediaPlayer;
	ProgressDialog progressDialog;
	FunctionPrx function;
	Communicator ic;
	Button playButton;
	Button stopButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		playButton = (Button) findViewById(R.id.playButton);
		stopButton = (Button) findViewById(R.id.stopButton);

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onClickPlayButton(view);
			}
		});

		stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onClickStopButton(view);
			}
		});
		//progressDialog = new ProgressDialog(this);

	}

	public void onClickPlayButton(View v) {
		System.out.println("Click");

		TextView tv = (TextView)findViewById(R.id.playButton);
		String textBtn = tv.getText().toString();
		System.out.println(textBtn);
		String modify = "";

		try {
			// Init Ice communication with server
			ic = Util.initialize();
			function = FunctionPrx.checkedCast(ic.stringToProxy("server:tcp -h 10.0.2.2 -p 10000"));
			if (function == null) {
				throw new Error("Invalid proxy");
			}

			// Handle PLAY/PAUSE action by changing textButton
			if(textBtn.equals("PLAY")){
				startPlayer();
				modify = "PAUSE";
				tv.setText(modify);
			}else if(textBtn.equals("PAUSE")) {
				mediaPlayer.pause();
				modify = "PLAY";
				tv.setText(modify);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startPlayer() {
		try {
			// Do not create a mediaPlayer if it already exists
			if(mediaPlayer == null) {
				function.playMusicAsync();
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setDataSource("http://10.0.2.2:8080");
				mediaPlayer.prepare();
				mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mediaPlayer.release();
					}
				});
			}
			// Out of the loop for PAUSE case
			mediaPlayer.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void onClickStopButton(View v) {
		try {
			System.out.println("ClickStop");
			// Do not call function methods if not initialized
			if(function != null) {
				function.stopMusic();
				mediaPlayer.reset();
				mediaPlayer.release();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
