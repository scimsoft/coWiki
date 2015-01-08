package com.scimsoft.cowiki.providers;

import java.util.List;
import java.util.Locale;

import com.scimsoft.cowiki.MainActivity;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

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
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			 Locale loc = new Locale ("spa", "ESP");
			
			int result = tts.setLanguage(loc);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
		
		tts.setOnUtteranceProgressListener(new UtteranceProgressListener()
        {
            @Override
            public void onDone(String utteranceId)
            {
                
            }

			@Override
			public void onStart(String utteranceId) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(String utteranceId) {
				// TODO Auto-generated method stub
				
			}

            
        });
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

}