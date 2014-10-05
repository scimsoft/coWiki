package com.scimsoft.cowiki;

import android.speech.tts.TextToSpeech;

public class SpeechProvider extends Providers{

	public SpeechProvider(MainActivity mainActivity) {
		super(mainActivity);
		// TODO Auto-generated constructor stub
	}
	
	public void speakOut(String text) {

		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

}
