package com.example.sidkathuria14.speech;

import android.app.Activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    private Synthesizer m_syn;EditText etInput;String input;
    Spinner language,voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = (EditText)findViewById(R.id.etinput);
        language = (Spinner)findViewById(R.id.languageSpinner);
        voice = (Spinner)findViewById(R.id.voiceSpinner);


        String [] lang = new String[]{
                "fr-FR","fr-CA","fi-FI","es-MX","hi-IN","en-US","es-ES","pl-PL"
        };
        String [] voic = new String[]{
                "male","female"
        };
        ArrayAdapter<String> dataAdapterVoice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, voic);


        dataAdapterVoice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> dataAdapterLanguage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lang);


        dataAdapterVoice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        language.setAdapter(dataAdapterLanguage);
        voice.setAdapter(dataAdapterVoice);





        if (getString(R.string.api_key).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        } else {

            if (m_syn == null) {
                // Create Text To Speech Synthesizer.
                m_syn = new com.example.sidkathuria14.speech.Synthesizer(getString(R.string.api_key));
            }

            Toast.makeText(this, "If the wave is not played, please see the log for more information.", Toast.LENGTH_LONG).show();

            m_syn.SetServiceStrategy(com.example.sidkathuria14.speech.Synthesizer.ServiceStrategy.AlwaysService);

            Voice v = new Voice("hi-IN", "Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)", Voice.Gender.Male, true);
            //Voice v = new Voice("zh-CN", "Microsoft Server Speech Text to Speech Voice (zh-CN, HuihuiRUS)", Voice.Gender.Female, true);
            m_syn.SetVoice(v, null);

            // Use a string for speech.
            m_syn.SpeakToAudio("Greetings, user!");
            //getString(R.string.tts_text)

            // Use SSML for speech.
            String text = "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xml:lang=\"en-US\"><voice xml:lang=\"en-US\" name=\"Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)\">You can also use SSML markup for text to speech.</voice></speak>";
//            m_syn.SpeakSSMLToAudio(text);

            findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "stoppped", Toast.LENGTH_SHORT).show();
                    m_syn.stopSound();
                }
            });

            findViewById(R.id.play_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "playing!", Toast.LENGTH_SHORT).show();
                    input = etInput.getText().toString();
                    m_syn.SpeakToAudio(input);
                }
            });
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
