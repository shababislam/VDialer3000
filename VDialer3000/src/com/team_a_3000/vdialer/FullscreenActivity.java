package com.team_a_3000.vdialer;

import java.util.ArrayList;
import java.util.Locale;

import com.team_a_3000.vdialer.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.speech.RecognizerIntent;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */


public class FullscreenActivity extends Activity {
	EditText test;
	Button callButton;
	static final int CALL_REQUEST = 1;
	static final int MAKE_CALL = 2;
	static String numberToCall = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.

		test = (EditText)this.findViewById(R.id.testId);
		callButton = (Button)this.findViewById(R.id.callButton);
		callButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if (v == callButton){
					 listenUp("What do you want to do?", CALL_REQUEST);
					 /*
					 test.setText("pls work");
					 Intent makeCall = new Intent(Intent.ACTION_CALL);
					 makeCall.setData(Uri.parse("tel:6136956624"));
					 startActivity(makeCall);
					 
					 */
				 }

			}
		});
	}

	private void listenUp(String message, int sendCode){
		Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		 
		 speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		 speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, message);
		 speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
		 speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.ENGLISH);
		 
		 startActivityForResult(speechIntent,sendCode);
		 
	}
	
	@Override
	public void onActivityResult(int req, int res, Intent data){
		ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		test = (EditText)this.findViewById(R.id.testId);

		if(req == CALL_REQUEST){
			

			test.setText(result.get(0));
			if(result.get(0).equals("call")){
				listenUp("What number would you like to call?", MAKE_CALL);
			}
		}
		
		if(req == MAKE_CALL){
			numberToCall = result.get(0).replaceAll("\\s+","");
			test.setText("the number is: "+ numberToCall);
			Intent makeCall = new Intent(Intent.ACTION_CALL);
			makeCall.setData(Uri.parse("tel:"+numberToCall));
			startActivity(makeCall);

		}
	}
	
}
