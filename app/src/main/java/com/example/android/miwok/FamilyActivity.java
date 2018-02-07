package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    AudioManager audioManager;

    /**
     * its used for controlling the audio and accordingly changing the state of the it.
     */
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener =new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange){
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    mediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.pause();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words=new ArrayList<>();
        words.add(new Word("father","pappa",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","mummy",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","beta",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","betee",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older son","bada beta",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger son","chota beta",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older daughter","badi betee",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger daughter","chooti betee",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grand mother","dadi",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grand father","dada",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter arrayAdapter=new WordAdapter(this,words,R.color.category_family);
        ListView listView=findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //   Word word= words.get(i);
                Word word= (Word) adapterView.getItemAtPosition(i);
                //In case user click multiple item quickly
                releaseMediaPlayer();

                if (audioManager != null) {
                    if(audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioResourceId());
                        mediaPlayer.start();

                        // when the audio completes playing callback//
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                releaseMediaPlayer();
                            }
                        });
                    }
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
