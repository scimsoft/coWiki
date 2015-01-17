package com.scimsoft.whatsnear.providers;

import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.scimsoft.whatsnear.MainActivity;

public class SpeechProvider extends Providers{
	private TextToSpeech tts;
	public SpeechProvider(MainActivity mainActivity) {
		super(mainActivity);
		tts = new TextToSpeech(mainActivity, mainActivity);
		
		
		// TODO Auto-generated constructor stub
	}
	
	public void addSpeechQueue(String text) {
		tts.speak(text, TextToSpeech.QUEUE_ADD, null);
		
	}
	public void addSpeechQueueWithSilence(String text){
		addSpeechQueue(text);
		tts.playSilence(1800, TextToSpeech.QUEUE_ADD, null);
	}
	@SuppressLint("NewApi")
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			
			
			int result = tts.setLanguage(mainActivity.localeProvider.getLocale());

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
		
	}

	public void speekResults(List<String> results) {
		for (String res : results) {
			addSpeechQueue(res);			
		}
		
	}

	public void speekWikiExtract(String extract) {
		tts.stop();
		addSpeechQueue(extract);		
	}
	
	public void interuptSpeech(){
		if (tts != null) {
			tts.stop();
			}
	}

	public void speekStringList(List<String> extract) {
		tts.stop();
		Iterator<String> it = extract.iterator();
		while(it.hasNext()){
			addSpeechQueueWithSilence(it.next());			
		}
	}

}
