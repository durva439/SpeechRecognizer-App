package com.example.iot;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loadinganimation.LoadingAnimation;
import com.example.speechrecognizer.R;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int SPEECH_REQUEST_CODE = 0;
    private SpeechRecognizer iot;
    TextView txt;
    LoadingAnimation loadingAnim;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vibrator vibrator;
        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);


        txt = findViewById(R.id.textView);
        loadingAnim = findViewById(R.id.loadingAnim);
        loadingAnim.setVisibility(View.INVISIBLE);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        startSpeechRecognition();
    }

    private void startSpeechRecognition() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                loadingAnim.setVisibility(View.VISIBLE);
                loadingAnim.setProgressVector(getDrawable(R.drawable.mic));
                loadingAnim.setTextViewVisibility(true);
                loadingAnim.setTextStyle(true);
                //loadingAnim.setTextColor(Color.WHITE);
                //loadingAnim.setTextSize(12F);
                loadingAnim.setTextMsg("");
                loadingAnim.setEnlarge(6);
            }

            @Override

            public void onBeginningOfSpeech() {
                loadingAnim.setTextMsg("Listening");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                startSpeechRecognition();
                onBeginningOfSpeech();

            }

            @Override
            public void onResults(Bundle results) {
                loadingAnim.setVisibility(View.INVISIBLE);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);


                ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (result != null && result.size() > 0) {
                    if (Build.VERSION.SDK_INT >= 26 && result.get(0).toString().equals("Balaji"))
                    {
                        vibrator.vibrate(VibrationEffect.createOneShot(1200, VibrationEffect.DEFAULT_AMPLITUDE));
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_CALL_SIGNAL_ISDN_INTERGROUP,400);
                        startSpeechRecognition();
                        onBeginningOfSpeech();

                    }
                    else if (Build.VERSION.SDK_INT >= 26 && result.get(0).toString().equals("Durva")){
                        vibrator.vibrate(VibrationEffect.createOneShot(1200, VibrationEffect.DEFAULT_AMPLITUDE));
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK,400);
                        startSpeechRecognition();
                        onBeginningOfSpeech();
                    }
                    else if (Build.VERSION.SDK_INT >= 26 && result.get(0).toString().equals("Aishwarya")){
                        vibrator.vibrate(VibrationEffect.createOneShot(1200, VibrationEffect.DEFAULT_AMPLITUDE));
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK,400);
                        startSpeechRecognition();
                        onBeginningOfSpeech();}
                    else {
                        txt.setText("Sorry "+result.get(0).toString()+" You are not in our Database");
                        txt.setTextColor(Color.WHITE);
                    }

                }
            }

            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        speechRecognizer.startListening(intent);
    }
}