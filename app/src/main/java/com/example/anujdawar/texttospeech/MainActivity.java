package com.example.anujdawar.texttospeech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static TextToSpeech textToSpeech;
    private static int result;
    private static String textToConvert = "";
    private ImageButton imgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i == TextToSpeech.SUCCESS)
                    result = textToSpeech.setLanguage(Locale.getDefault());

                else
                    Toast.makeText(MainActivity.this, "Your phone doesn't support this feature yo", Toast.LENGTH_SHORT).show();
            }
        });

        imgButton = (ImageButton)findViewById(R.id.imageButton);

        onMicClickListener();

    }

    public void onMicClickListener()
    {
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechRecognition();
            }
        });
    }

    public void promptSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something?");

        try {
            startActivityForResult(intent, 100);
        }catch (ActivityNotFoundException a)
        {
            Toast.makeText(MainActivity.this, "Your Device Doesn't Support This Language", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent intent)
    {
        super.onActivityResult(request_code, result_code, intent);

        switch(request_code)
        {
            case 100:
                if(result_code == RESULT_OK && intent != null)
                {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textToConvert = (result.get(0));

                    textToConvert = textToConvert.toLowerCase();

                    if(textToConvert.contains("dev") || textToConvert.contains("deb"))
                    {
                        if (textToConvert.contains("one") || textToConvert.contains("1")) {
                            if (textToConvert.contains("on"))
                                textToConvert = "Device 1 turned ON";
                            else
                                textToConvert = "Device 1 turned OFF";
                        }

                        else if(textToConvert.contains("1") || textToConvert.contains("two")) {
                            if (textToConvert.contains("on"))
                                textToConvert = "Device 2 turned ON";
                            else
                                textToConvert = "Device 2 turned OFF";
                        }

                        else if(textToConvert.contains("3") || textToConvert.contains("three")) {
                            if (textToConvert.contains("on"))
                                textToConvert = "Device 3 turned ON";
                            else
                                textToConvert = "Device 3 turned OFF";
                        }

                        else if(textToConvert.contains("4") || textToConvert.contains("four")) {
                            if (textToConvert.contains("on"))
                                textToConvert = "Device 4 turned ON";
                            else
                                textToConvert = "Device 4 turned OFF";
                        }

                        else if(textToConvert.contains("5")) {
                            if (textToConvert.contains("on"))
                                textToConvert = "Device 5 turned ON";
                            else
                                textToConvert = "Device 5 turned OFF";
                        }
                    }
                }
                    SpeakButtonListener();
                break;
        }
    }

    public void SpeakButtonListener()
    {
        if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
            Toast.makeText(MainActivity.this, "your device doesn't support this feature" , Toast.LENGTH_SHORT).show();

        else
            textToSpeech.speak(textToConvert,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
