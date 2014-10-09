package com.scimsoft.cowiki;

import java.util.List;
import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.util.Log;

public class SpeechProvider extends Providers{
	private TextToSpeech tts;
	public SpeechProvider(MainActivity mainActivity) {
		super(mainActivity);
		tts = new TextToSpeech(mainActivity, mainActivity);
		// TODO Auto-generated constructor stub
	}
	
	public void speakOut(String text) {

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// Locale loc = new Locale ("spa", "ESP");
			Locale loc = new Locale("nl", "NL");
			int result = tts.setLanguage(loc);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}
	
	public void onDestroy() {
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		mainActivity.onDestroy();
	}

	public void speekResults(List<String> results) {
		for (String res : results) {
			speakOut(res);
			while (tts.isSpeaking()) 
			{
			}
		}
		
	}

}
