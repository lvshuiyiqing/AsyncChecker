package com.example.earlycancel;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private ChildTasker mChildTasker = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mChildTasker = new ChildTasker(findViewById(R.id.button1));
		
		mChildTasker.start();
	}


	@Override
	protected void onDestroy() {
		if (mChildTasker != null) {
			mChildTasker.cancel(true);
		}
		super.onDestroy();
	}

}
