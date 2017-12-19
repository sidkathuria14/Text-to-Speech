package com.example.sidkathuria14.speech;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;



public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{
    private Synthesizer m_syn_male,m_syn_female;EditText etInput;String input;
   public String item = "male";
    Spinner language_spinner,voice_spinner;
    String voice = "male";
    public static final String TAG = "speech";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = (EditText)findViewById(R.id.etinput);
        language_spinner = (Spinner)findViewById(R.id.languageSpinner);
        voice_spinner = (Spinner)findViewById(R.id.voiceSpinner);


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


        language_spinner.setAdapter(dataAdapterLanguage);
        voice_spinner.setAdapter(dataAdapterVoice);


        findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "stoppped", Toast.LENGTH_SHORT).show();
               if(item.equals("male")) {
                   male_voice();
                   m_syn_male.stopSound();
               }
               else if(item.equals("female")){
                   female_voice();
                   m_syn_female.stopSound();
               }
            }
        });

        findViewById(R.id.play_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "playing!", Toast.LENGTH_SHORT).show();
                input = etInput.getText().toString();
                if(item.equals("male")){
                    Log.d(TAG, "onClick: "+ "play button - male");
                    male_voice();
                    m_syn_male.SpeakToAudio(input);
                }
                else if(item.equals("female")){
                    Log.d(TAG, "onClick: "+ "play button - female");
                    female_voice();
                    m_syn_female.SpeakToAudio(input);
                }

            }
        });



    }
    public void male_voice(){
        Log.d(TAG, "male_voice: ");
        if (getString(R.string.api_key).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        } else {

            if (m_syn_male == null) {
                // Create Text To Speech Synthesizer.
                m_syn_male = new com.example.sidkathuria14.speech.Synthesizer(getString(R.string.api_key));
            }

            Toast.makeText(this, "If the wave is not played, please see the log for more information.", Toast.LENGTH_LONG).show();

            m_syn_male.SetServiceStrategy(com.example.sidkathuria14.speech.Synthesizer.ServiceStrategy.AlwaysService);

            Voice v = new Voice("hi-IN", "Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)", Voice.Gender.Male, true);
            //Voice v = new Voice("zh-CN", "Microsoft Server Speech Text to Speech Voice (zh-CN, HuihuiRUS)", Voice.Gender.Female, true);
            m_syn_male.SetVoice(v, null);

            // Use a string for speech.
//            m_syn_male.SpeakToAudio("Greetings, user!");
            //getString(R.string.tts_text)

            // Use SSML for speech.
            String text = "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xml:lang=\"en-US\"><voice xml:lang=\"en-US\" name=\"Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)\">You can also use SSML markup for text to speech.</voice></speak>";
//            m_syn.SpeakSSMLToAudio(text);


        }
    }
    public void female_voice(){
        Log.d(TAG, "female_voice: ");
        if (getString(R.string.api_key).startsWith("Please")) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.add_subscription_key_tip_title))
                    .setMessage(getString(R.string.add_subscription_key_tip))
                    .setCancelable(false)
                    .show();
        } else {

            if (m_syn_female == null) {
                // Create Text To Speech Synthesizer.
                m_syn_female = new com.example.sidkathuria14.speech.Synthesizer(getString(R.string.api_key));
            }

            Toast.makeText(this, "If the wave is not played, please see the log for more information.", Toast.LENGTH_LONG).show();

            m_syn_female.SetServiceStrategy(com.example.sidkathuria14.speech.Synthesizer.ServiceStrategy.AlwaysService);

            Voice v = new Voice("hi-IN", "Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)", Voice.Gender.Female, true);
            //Voice v = new Voice("zh-CN", "Microsoft Server Speech Text to Speech Voice (zh-CN, HuihuiRUS)", Voice.Gender.Female, true);
            m_syn_female.SetVoice(v, null);

            // Use a string for speech.
//            m_syn_female.SpeakToAudio("Greetings, user!");
            //getString(R.string.tts_text)

            // Use SSML for speech.
            String text = "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" xmlns:mstts=\"http://www.w3.org/2001/mstts\" xml:lang=\"en-US\"><voice xml:lang=\"en-US\" name=\"Microsoft Server Speech Text to Speech Voice (en-US, ZiraRUS)\">You can also use SSML markup for text to speech.</voice></speak>";
//            m_syn.SpeakSSMLToAudio(text);


        }
    }
    //spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item



        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.voiceSpinner)
        {
            //do this
            item = parent.getItemAtPosition(position).toString();

            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            if(item.equals("male")){
                Log.d(TAG, "onItemSelected: male");
            }
            else if(item.equals("female")){
                Log.d(TAG, "onItemSelected: female");
            }
        }
        else if(spinner.getId() == R.id.languageSpinner)
        {
            //do this
        }


        // Showing selected spinner item

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.stt){
startActivity(new Intent(MainActivity.this,stt.class));
        }
        if(item.getItemId() == R.id.tts){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.speech,menu);
        return super.onCreateOptionsMenu(menu);

    }
}
