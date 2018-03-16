package com.androidapp;

import android.annotation.TargetApi;
import android.net.sip.SipAudioCall;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.Util;
import mp3App.*;

import static mp3App.FunctionPrx.checkedCast;

public class Client extends AppCompatActivity {

	private boolean _destroyed = false;
	private Handler _handler;
	private Communicator _communicator;
	private String _loginError;
	private SipAudioCall.Listener _loginListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
	}

	public void onClickPlayButton(View v) {
		System.out.println("Click");

		try {
			com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize();
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
