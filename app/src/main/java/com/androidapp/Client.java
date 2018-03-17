package com.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import mp3App.*;

public class Client extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
	}

	public void onClickPlayButton(View v) {
		System.out.println("Click");

		try {
			Communicator ic = Util.initialize();
			FunctionPrx function = FunctionPrx.checkedCast(ic.stringToProxy("server:tcp -h 10.0.2.2 -p 10000"));
			if (function == null) {
				throw new Error("Invalid proxy");
			}
			function.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
